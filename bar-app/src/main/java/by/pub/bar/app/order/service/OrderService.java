package by.pub.bar.app.order.service;


import by.pub.bar.app.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();

    Order findById(String id);

    Order saveOrder(Order Order);

    void deleteOrderById(String id);

    Order acceptOrder(String id);
}
