package by.pub.storage.app.element.ingredient_request.controller;

import by.pub.storage.app.element.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.element.ingredient_request.service.IngredientRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredient")
public class IngredientRequestController {
    private final IngredientRequestService ingredientRequestService;

    public IngredientRequestController(IngredientRequestService ingredientRequestService) {
        this.ingredientRequestService = ingredientRequestService;
    }

    @PostMapping
    public IngredientRequest saveIngredientRequest(@RequestBody IngredientRequest ingredientRequest) {
        return ingredientRequestService.saveIngredientRequest(ingredientRequest);
    }

    @GetMapping
    public List<IngredientRequest> findAll() {
        return ingredientRequestService.findAllIngredientRequests();
    }
}
