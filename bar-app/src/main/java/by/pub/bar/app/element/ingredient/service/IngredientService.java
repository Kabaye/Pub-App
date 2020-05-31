package by.pub.bar.app.element.ingredient.service;


import by.pub.bar.app.element.ingredient.entity.Ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> findAllIngredients();

    Ingredient findIngredientByName(String name);

    Ingredient saveIngredient(Ingredient ingredient);

    void deleteIngredientByName(String name);

    Ingredient takeIngredientFromBarStand(Ingredient ingredient);

    boolean checkForAvailability(Ingredient ingredient);

    Ingredient putIngredientOnBarStand(Ingredient ingredient);
}
