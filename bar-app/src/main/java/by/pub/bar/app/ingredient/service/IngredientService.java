package by.pub.bar.app.ingredient.service;


import by.pub.bar.app.ingredient.entity.Ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> findAllIngredients();

    Ingredient findIngredientByName(String name);

    Ingredient findIngredientById(String id);

    Ingredient saveIngredient(Ingredient ingredient);

    void deleteIngredientByName(String name);

    Ingredient takeIngredientsFromBarStand(String ingredientName, Long amount);
}
