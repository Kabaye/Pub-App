package by.pub.bar.app.websocket.client;

import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import by.pub.bar.app.websocket.event.SendIngredientRequestEvent;
import by.pub.bar.app.websocket.handler.MyStompSessionHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomWebSocketClient {
    private final MyStompSessionHandler stompSessionHandler;

    public CustomWebSocketClient(MyStompSessionHandler myStompSessionHandler) {
        this.stompSessionHandler = myStompSessionHandler;
    }

    public void requestIngredient(IngredientRequest ingredientRequest) {
        stompSessionHandler.sendData("/storage-app/request-ingredient", ingredientRequest);
    }

    @EventListener
    public void listenRequestIngredient(SendIngredientRequestEvent ingredientRequestEvent) {
        requestIngredient(ingredientRequestEvent.getIngredientRequest());
    }
}
