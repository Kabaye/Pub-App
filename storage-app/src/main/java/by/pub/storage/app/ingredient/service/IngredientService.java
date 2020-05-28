package by.pub.storage.app.ingredient.service;

import by.pub.storage.app.ingredient.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAllIngredients();

    Ingredient orderIngredients(String name, Long amount);

    Ingredient findIngredientByName(String name);

    Ingredient findIngredientById(String id);
}
