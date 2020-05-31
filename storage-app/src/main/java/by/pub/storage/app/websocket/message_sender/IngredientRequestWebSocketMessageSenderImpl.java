package by.pub.storage.app.websocket.message_sender;

import by.pub.storage.app.element.ingredient_request.entity.IngredientRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class IngredientRequestWebSocketMessageSenderImpl implements
    IngredientRequestWebSocketMessageSender {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public IngredientRequestWebSocketMessageSenderImpl(
        SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendAcceptedIngredientRequest(IngredientRequest ingredientRequest) {
        simpMessagingTemplate
            .convertAndSend("/storage-app/accept-ingredient-request", ingredientRequest);
    }
}
