package com.example.product_service.services;

import com.example.product_service.models.Product;


public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    Product getProduct(Long id);

    void deleteProduct(Long id);

}
