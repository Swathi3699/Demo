package com.microservice.demo.demo.service;

import com.microservice.demo.demo.common.Payment;
import com.microservice.demo.demo.common.TransactionRequest;
import com.microservice.demo.demo.common.TransactionResponse;
import com.microservice.demo.demo.entity.Order;
import com.microservice.demo.demo.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RestTemplate template;


    public TransactionResponse saveOrder(TransactionRequest request) {
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId()); // Set the order ID
        payment.setAmount(order.getPrice()); // Set the amount

        // Call the payment service
        Payment paymentResponse = template.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);

        String response;
        if (paymentResponse != null && paymentResponse.getPaymentStatus().equals("success")) {
            response = "Payment processing successful and order placed.";
            orderRepo.save(order); // Save the order only if payment is successful
        } else {
            response = "There is a failure in payment API, order added to cart.";
            // Consider saving the order with a status indicating that it's pending or failed
        }

        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionUID(), response);
    }
}
