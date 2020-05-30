package by.pub.bar.app.ingredient_request.controller;

import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import by.pub.bar.app.ingredient_request.service.IngredientRequestService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredient-requests")
public class IngredientRequestController {
    private final IngredientRequestService ingredientRequestService;

    public IngredientRequestController(IngredientRequestService ingredientRequestService) {
        this.ingredientRequestService = ingredientRequestService;
    }

    @PostMapping
    public void saveIngredientRequest(@RequestBody IngredientRequest ingredientRequest) {
        ingredientRequestService.createAndSendIngredientRequest(ingredientRequest.getIngredientName(), ingredientRequest.getIngredientAmount());
    }

    @GetMapping
    public List<IngredientRequest> findAll() {
        return ingredientRequestService.findAllIngredientRequests();
    }

    @DeleteMapping
    public void deleteByRequestId(String requestId) {
        ingredientRequestService.deleteByRequestId(requestId);
    }
}
