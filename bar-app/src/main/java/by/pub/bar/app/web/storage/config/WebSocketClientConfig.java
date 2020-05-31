package by.pub.bar.app.web.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class WebSocketClientConfig {
    @Bean
    public WebSocketStompClient webSocketStompClient() {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        webSocketStompClient.setTaskScheduler(new ThreadPoolTaskScheduler());
        return webSocketStompClient;
    }

    @Bean
    public String websocketHandshakeURL() {
        return "ws://localhost:9890/ws";
    }
}
