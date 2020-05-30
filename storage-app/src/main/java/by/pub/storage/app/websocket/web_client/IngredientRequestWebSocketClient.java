package by.pub.storage.app.websocket.web_client;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;

public interface IngredientRequestWebSocketClient {
    void acceptIngredientRequest(IngredientRequest ingredientRequest);
}
