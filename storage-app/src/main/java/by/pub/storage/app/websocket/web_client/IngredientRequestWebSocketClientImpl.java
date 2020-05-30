package by.pub.storage.app.websocket.web_client;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class IngredientRequestWebSocketClientImpl implements IngredientRequestWebSocketClient {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public IngredientRequestWebSocketClientImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void acceptIngredientRequest(IngredientRequest ingredientRequest) {
        simpMessagingTemplate.convertAndSend("/storage-app/accept-ingredient-request", ingredientRequest);
    }
}
