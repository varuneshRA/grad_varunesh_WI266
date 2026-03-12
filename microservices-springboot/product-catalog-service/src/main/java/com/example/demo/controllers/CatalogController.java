package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.beans.Product;
import com.example.demo.services.CatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CatalogController {

    @Autowired
    CatalogService cs;

    @GetMapping("/catalog")
    public List<Product> getAllProducts() {
        return cs.getAllProducts();
    }

}
