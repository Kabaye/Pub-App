package by.pub.storage.app.websocket.message_sender;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;

public interface IngredientRequestWebSocketMessageSender {
    void sendAcceptedIngredientRequest(IngredientRequest ingredientRequest);
}
