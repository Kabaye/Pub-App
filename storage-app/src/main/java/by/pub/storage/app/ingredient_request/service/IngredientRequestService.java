package by.pub.storage.app.ingredient_request.service;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;

import java.util.List;

public interface IngredientRequestService {
    IngredientRequest findByRequestId(String requestId);

    List<IngredientRequest> findAllIngredientRequests();

    IngredientRequest saveIngredientRequest(IngredientRequest ingredientRequest);

    IngredientRequest acceptIngredientRequest(IngredientRequest ingredientRequest);

    void deleteByRequestId(String requestId);
}
