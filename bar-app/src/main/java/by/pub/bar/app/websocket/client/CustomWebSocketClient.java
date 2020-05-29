package by.pub.bar.app.websocket.client;

import lombok.SneakyThrows;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class CustomWebSocketClient {
    private final WebSocketStompClient webSocketStompClient;
    private final StompSessionHandler stompSessionHandler;

    @SneakyThrows
    public CustomWebSocketClient(StompSessionHandler myStompSessionHandler) {
        this.stompSessionHandler = myStompSessionHandler;
        this.webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.webSocketStompClient.setTaskScheduler(new ThreadPoolTaskScheduler());


        StompSession session = webSocketStompClient.connect("ws://localhost:9890/ws", stompSessionHandler).get();

    }
}
