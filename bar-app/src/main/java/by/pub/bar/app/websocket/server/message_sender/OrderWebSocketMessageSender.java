package by.pub.bar.app.websocket.server.message_sender;

import by.pub.bar.app.order.entity.Order;

public interface OrderWebSocketMessageSender {
    void sendAcceptedOrder(Order order);
}
