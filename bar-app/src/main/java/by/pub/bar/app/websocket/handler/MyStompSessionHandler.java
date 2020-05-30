package by.pub.bar.app.websocket.handler;

import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import by.pub.bar.app.websocket.client.CustomWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebSocketClient.class);

    private final String websocketHandshakeURL;
    private final WebSocketStompClient webSocketStompClient;

    private volatile StompSession stompSession;

    protected MyStompSessionHandler(String websocketHandshakeURL, WebSocketStompClient webSocketStompClient) {
        this.websocketHandshakeURL = websocketHandshakeURL;
        this.webSocketStompClient = webSocketStompClient;

        try {
            stompSession = this.webSocketStompClient.connect(this.websocketHandshakeURL, this).get();
        } catch (Exception exc) {
            LOGGER.error("Cannot connect to storage app", exc);
        }
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/storage-app/accept-ingredient-request", this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOGGER.error("Received error on socket channel with session id: " + session.getSessionId(), exception);
    }

    //TODO 30.05.2020 check this
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return IngredientRequest.class;
    }

    //TODO 30.05.2020 Use service here
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        IngredientRequest msg = (IngredientRequest) payload;
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> {
            ListenableFuture<StompSession> future = webSocketStompClient.connect(websocketHandshakeURL, this);
            try {
                stompSession = future.get();
            } catch (InterruptedException | ExecutionException exc) {
                LOGGER.error("Cannot connect to storage app", exc);
            }

        };

        service.schedule(runnable, 5_000, TimeUnit.MILLISECONDS);

//        service.awaitTermination();
    }

    public void sendData(String destination, Object payload) {
        if (stompSession.isConnected()) {
            stompSession.send(destination, payload);
            return;
        }
        LOGGER.warn("StompSession is not connected {}.", stompSession);
    }
}