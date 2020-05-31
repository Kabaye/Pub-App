package by.pub.bar.app.element.ingredient.controller;

import by.pub.bar.app.element.ingredient.entity.Ingredient;
import by.pub.bar.app.element.ingredient.service.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<Ingredient> findAllIngredients() {
        return ingredientService.findAllIngredients();
    }

    @PostMapping
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.saveIngredient(ingredient);
    }
}
