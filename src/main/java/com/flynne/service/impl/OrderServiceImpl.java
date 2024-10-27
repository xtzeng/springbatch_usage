package com.flynne.service.impl;

/**
 * @author xiaoti
 * @date 2024/10/27 21:49
 */

import com.flynne.mapper.OrderMapper;
import com.flynne.model.Order;
import com.flynne.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void createOrder(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.update(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderMapper.deleteById(id);
    }
}