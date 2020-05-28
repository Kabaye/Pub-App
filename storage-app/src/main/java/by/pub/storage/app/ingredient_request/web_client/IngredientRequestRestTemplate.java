package by.pub.storage.app.ingredient_request.web_client;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;

public interface IngredientRequestRestTemplate {
    void acceptIngredientRequest(IngredientRequest ingredientRequest);
}
