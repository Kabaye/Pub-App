package by.pub.bar.app.element.order.service;


import by.pub.bar.app.element.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();

    Order saveOrder(Order Order);

    void deleteOrderById(String id);

    Order acceptOrder(Order order);
}
