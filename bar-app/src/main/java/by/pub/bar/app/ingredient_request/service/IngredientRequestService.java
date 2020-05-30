package by.pub.bar.app.ingredient_request.service;


import by.pub.bar.app.ingredient_request.entity.IngredientRequest;

import java.util.List;

public interface IngredientRequestService {
    IngredientRequest findByRequestId(String requestId);

    List<IngredientRequest> findAllIngredientRequests();

    void deleteByRequestId(String requestId);

    void createAndSendIngredientRequest(String ingredientName, Long amount);

    void acceptIngredientRequest(IngredientRequest ingredientRequest);
}
