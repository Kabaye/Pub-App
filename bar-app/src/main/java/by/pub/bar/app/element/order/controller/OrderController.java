package by.pub.bar.app.element.order.controller;

import by.pub.bar.app.element.order.service.OrderService;
import by.pub.bar.app.web.client.converter.OrderConverter;
import by.pub.bar.app.web.client.dto.OrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    public OrderController(OrderService orderService, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
    }

    @PostMapping
    public OrderDTO saveOrder(@RequestBody OrderDTO orderDTO) {
        return orderConverter.toDTO(orderService.saveOrder(orderConverter.toEntity(orderDTO)));
    }
}
