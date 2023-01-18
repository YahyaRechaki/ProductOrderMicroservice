package com.example.product_service.services;

import com.example.product_service.exceptions.OrderNotFoundException;
import com.example.product_service.exceptions.ProductNotFoundException;
import com.example.product_service.models.Order;
import com.example.product_service.models.Product;
import com.example.product_service.repositories.OrderRepository;
import com.example.product_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrder(Order order) {
        order.calculateTotalAmount();
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id : " + order.getId()));
        existingOrder.setCustomerName(order.getCustomerName());
        existingOrder.setProducts(order.getProducts());
        existingOrder.calculateTotalAmount();
        return orderRepository.save(existingOrder);

    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsByOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        return order.getProducts();
    }

    @Override
    public Order addProductToOrder(Long orderId, Long productId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        existingOrder.getProducts().add(product);
        existingOrder.calculateTotalAmount();
        return orderRepository.save(existingOrder);
    }

    @Override
    public Order removeProductFromOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        order.getProducts().remove(product);
        return orderRepository.save(order);
    }







}
