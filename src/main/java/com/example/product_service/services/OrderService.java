package com.example.product_service.services;

import com.example.product_service.models.Order;
import com.example.product_service.models.Product;
import com.example.product_service.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order updateOrder(Long id, Order order);
    Order getOrder(Long id);
    void deleteOrder(Long id);

    List<Product> getProductsByOrder(Long id);

    Order addProductToOrder(Long orderId, Long productId);

    Order removeProductFromOrder(Long orderId, Long productId);
}
