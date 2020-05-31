package by.pub.bar.app.element.ingredient_request.service;


import by.pub.bar.app.element.ingredient_request.entity.IngredientRequest;

public interface IngredientRequestService {

    void deleteByRequestId(String requestId);

    IngredientRequest createAndSendIngredientRequest(String ingredientName, Long amount);
}
