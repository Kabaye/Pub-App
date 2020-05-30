package by.pub.bar.app.ingredient_request.service;


import by.pub.bar.app.ingredient_request.entity.IngredientRequest;

import java.util.List;

public interface IngredientRequestService {
    IngredientRequest findByRequestId(String requestId);

    List<IngredientRequest> findAllIngredientRequests();

    void deleteByRequestId(String requestId);

    IngredientRequest createAndSendIngredientRequest(String ingredientName, Long amount);
}
