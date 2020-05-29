package by.pub.storage.app.websocket.controller;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import by.pub.storage.app.ingredient_request.service.IngredientRequestService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Random;

@Controller
public class WebSocketController {
    private final IngredientRequestService ingredientRequestService;

    public WebSocketController(IngredientRequestService ingredientRequestService) {
        this.ingredientRequestService = ingredientRequestService;
    }

    @MessageMapping("/request-ingredient")
    @SendTo("/storage-app/accept-ingredient-request")
    public IngredientRequest send(final IngredientRequest req) {
        return req.setIngredientAmount((long) new Random().nextInt()).setStatus(IngredientRequestStatus.ACCEPTED);
    }
}
