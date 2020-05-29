package by.pub.bar.app.websocket.handler;

import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and
 * send a sample message to server.
 *
 * @author Kalyan
 */

@Component
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private final WebSocketStompClient webSocketStompClient;

    public MyStompSessionHandler() {
        this.webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.webSocketStompClient.setTaskScheduler(new ThreadPoolTaskScheduler());
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        session.subscribe("/storage-app/accept-ingredient-request", this);
        session.send("/storage-app/request-ingredient", new IngredientRequest().setIngredientAmount(new Random().nextLong()).setIngredientName("asda").setRequestId("123456789"));
        System.out.println("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception: " + exception.getMessage());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return IngredientRequest.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        IngredientRequest msg = (IngredientRequest) payload;
        System.out.println(msg);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> {
            System.out.println("Attempt to reconnect!");
            System.out.println(session.isConnected());

            ListenableFuture<StompSession> future = webSocketStompClient.connect("ws://localhost:9890/ws", this);
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }

        };

        service.schedule(runnable, 5_000, TimeUnit.MILLISECONDS);

//        service.awaitTermination();
    }
}