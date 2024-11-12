package com.first.demo.controller;

import com.first.demo.common.ProductResponse;
import com.first.demo.entity.Product;
import com.first.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping("/getP")
    public List<Product> getProducts(Product product){
        return service.getProducts(product);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int productId ){
        Optional<Product> productOptional = service.getProductById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            boolean isAvailable = product.getStockQuantity() > 0;
            ProductResponse productResponse = new ProductResponse(product, isAvailable);
            return ResponseEntity.ok(productResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<List<Product> > createProducts(@RequestBody List<Product> products){
        return service.createProducts(products);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProducts(@PathVariable int productId , @RequestBody Product product){
        return service.updateProducts(productId,product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProducts(@PathVariable int productId ){
        return service.deleteProducts(productId);
    }

}
