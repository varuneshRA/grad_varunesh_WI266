package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Product;

public interface ProductService {
    public String addProduct(Product p);
    public List<Product> getAllProducts();
    public Optional<Product> getProduct(int pid);
}
