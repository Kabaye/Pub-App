package by.pub.bar.app.order.controller;

import by.pub.bar.app.order.entity.Order;
import by.pub.bar.app.order.service.OrderService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping("/{orderId}")
    public Order findOrderById(@PathVariable String orderId) {
        return orderService.findById(orderId);
    }

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAllOrders();
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrderById(@PathVariable String orderId) {
        orderService.deleteOrderById(orderId);
    }
}
