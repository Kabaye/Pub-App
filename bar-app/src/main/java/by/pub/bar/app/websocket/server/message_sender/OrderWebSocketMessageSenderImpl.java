package by.pub.bar.app.websocket.server.message_sender;

import by.pub.bar.app.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderWebSocketMessageSenderImpl implements OrderWebSocketMessageSender {
    @Override
    public void sendAcceptedOrder(Order order) {

    }
}
