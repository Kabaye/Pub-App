package by.pub.storage.app.websocket.controller;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.service.IngredientRequestService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final IngredientRequestService ingredientRequestService;

    public WebSocketController(IngredientRequestService ingredientRequestService) {
        this.ingredientRequestService = ingredientRequestService;
    }

    @MessageMapping("/request-ingredient")
    public void send(IngredientRequest ingredientRequest) {
        ingredientRequestService.saveIngredientRequest(ingredientRequest);
    }
}
