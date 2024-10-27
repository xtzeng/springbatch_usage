package com.flynne.service;

/**
 * @author xiaoti
 * @date 2024/10/27 21:48
 */

import com.flynne.model.Order;

import java.util.List;

public interface IOrderService {
    void createOrder(Order order);

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    void updateOrder(Order order);

    void deleteOrder(Long id);
}