package by.pub.bar.app.web.web_client;

import by.pub.bar.app.order.entity.Order;

public interface WebClient {
    void sendAcceptedOrder(Order order);
}
