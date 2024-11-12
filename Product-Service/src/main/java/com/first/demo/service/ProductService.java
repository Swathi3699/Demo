package com.first.demo.service;

import com.first.demo.entity.Product;
import com.first.demo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    public List<Product> getProducts(Product product) {
        return repo.findAll();
    }

    public Optional<Product> getProductById(int productId) {
        return repo.findById(productId);
    }

    public ResponseEntity<List<Product>> createProducts(List<Product> products) {
        List<Product> records=repo.saveAll(products);
        return ResponseEntity.ok(records);
    }

    public ResponseEntity<Product> updateProducts(int productId, Product product) {
        Optional<Product> optionalProducts= repo.findById(productId);

        if(optionalProducts.isPresent()){
            Product existingRecords=optionalProducts.get();

            existingRecords.setProductId(product.getProductId());
            existingRecords.setName(product.getName());
            existingRecords.setDescription(product.getDescription());
            existingRecords.setPrice(product.getPrice());
            existingRecords.setStockQuantity(product.getStockQuantity());

            Product updateRecords=repo.save(existingRecords);
            return ResponseEntity.ok(updateRecords);
        }
        else {
            return ResponseEntity.notFound().build();
        }


    }
    public ResponseEntity<String> deleteProducts(int productId) {
        Optional<Product> optionalRecord=repo.findById(productId);

        if(optionalRecord.isPresent()){
            repo.deleteById(productId);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
}
