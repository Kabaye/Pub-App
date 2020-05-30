package by.pub.bar.app.ingredient.controller;

import by.pub.bar.app.ingredient.entity.Ingredient;
import by.pub.bar.app.ingredient.service.IngredientService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/ingredient")
    public Ingredient getIngredients(@RequestParam(required = false) String name, @RequestParam(required = false) String id) {
        if (Objects.nonNull(name)) {
            return ingredientService.findIngredientByName(name);
        }
        return ingredientService.findIngredientById(id);
    }

    @DeleteMapping("/{name}")
    public void deleteIngredient(@PathVariable String name) {
        ingredientService.deleteIngredientByName(name);
    }

    @PostMapping("/take-ingredient-from-bar-stand/{name}")
    public Ingredient takeIngredientFromBarStand(@PathVariable String name, @RequestParam Long amount) {
        return ingredientService.takeIngredientsFromBarStand(name, amount);
    }

}
