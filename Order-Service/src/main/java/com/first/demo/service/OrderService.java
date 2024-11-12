package com.first.demo.service;

import com.first.demo.common.*;
import com.first.demo.entity.Order;
import com.first.demo.repo.OrderRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RestTemplate restTemplate;
    public Optional<Order> getOrderById(int orderId) {

        return orderRepo.findById(orderId);
    }

//    public ResponseEntity<List<Order>> createOrder(List<Order> order) {
//        List<Order> records=orderRepo.saveAll(order);
//        return  ResponseEntity.ok(records);
//    }


    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(int orderId, Order order) {
        Optional<Order> existingRecordOptional=orderRepo.findById(orderId);

        if(existingRecordOptional.isPresent()){
            Order existingRecords=existingRecordOptional.get();

            existingRecords.setOrderId(order.getOrderId());
            existingRecords.setUserId(order.getUserId());
            existingRecords.setTotalAmount(order.getTotalAmount());
            existingRecords.setStatus(order.getStatus());

            Order updateRecords=orderRepo.save(existingRecords);
            return  ResponseEntity.ok(updateRecords);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    public String createOrder(OrderRequest request) throws Exception {
        String productUrl="http://PRODUCT-SERVICE/product/";
        String processUrl="http://PAYMENT-SERVICE/payment/process";
        ResponseEntity<ProductResponse> response = restTemplate.getForEntity(productUrl+request.getProductId(), ProductResponse.class);

        ProductResponse productResponse = response.getBody();



        if (productResponse != null && productResponse.isAvailable()) {
            Order order = request.getOrder();

            PaymentRequest paymentRequest = new PaymentRequest(order.getOrderId(), "CARD", order.getTotalAmount(), order.getUserId());
            ResponseEntity<PaymentResponse> paymentResponseEntity = restTemplate.postForEntity(processUrl, paymentRequest, PaymentResponse.class);
            PaymentResponse response1 = paymentResponseEntity.getBody();


            if (response1 != null && response1.getStatus().equals("SUCCESS")) {
                // Ensure the stock quantity is sufficient
                Product updateProduct = productResponse.getProduct();
                if (updateProduct.getStockQuantity() >= request.getQuantity()) {
                    orderRepo.save(order); // Save the order

                    updateProduct.setStockQuantity(updateProduct.getStockQuantity() - request.getQuantity());
                    restTemplate.put(productUrl + request.getProductId(), updateProduct);
                    return "Order created successfully and payment processed successfully. Transaction ID:" + response1.getTransactionId();
                } else {
                    throw new Exception("Not enough stock available");
                }
            } else {
                throw new Exception("Payment Failed");
            }
        }
            else{
                throw new Exception("Out of stock");
            }
        }
    // Circuit Breaker for PRODUCT-SERVICE
    @CircuitBreaker(name = "product-service", fallbackMethod = "productFallback")
    public ProductResponse getProductDetails(String productId) {
        String productUrl = "http://PRODUCT-SERVICE/product/";
        ResponseEntity<ProductResponse> response = restTemplate.getForEntity(productUrl + productId, ProductResponse.class);
        return response.getBody();
    }

    // Circuit Breaker for PAYMENT-SERVICE
    @CircuitBreaker(name = "payment-service", fallbackMethod = "paymentFallback")
    public PaymentResponse processPayment(Order order) {
        String processUrl = "http://PAYMENT-SERVICE/payment/process";
        PaymentRequest paymentRequest = new PaymentRequest(order.getOrderId(), "CARD", order.getTotalAmount(), order.getUserId());
        ResponseEntity<PaymentResponse> paymentResponseEntity = restTemplate.postForEntity(processUrl, paymentRequest, PaymentResponse.class);
        return paymentResponseEntity.getBody();
    }

    // Fallback for product-service
    public ProductResponse productFallback(String productId, Throwable throwable) {
        // Handle fallback logic for product service
        throw new RuntimeException("Product Service is down. Please try again later.");
    }

    // Fallback for payment-service
    public PaymentResponse paymentFallback(Order order, Throwable throwable) {
        // Handle fallback logic for payment service
        throw new RuntimeException("Payment Service is down. Please try again later.");
    }


}
