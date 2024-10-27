package com.flynne.controller;

import com.flynne.model.Order;
import com.flynne.service.IOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaoti
 * @date 2024/10/27 21:50
 */
@RestController
public class OrderController {

    @Resource
    private IOrderService orderService;

    @RequestMapping("getAllOrders")
    public List<Order> getAllOrders() {return orderService.getAllOrders();}
}
