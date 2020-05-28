package by.pub.storage.app.ingredient.provider;

import by.pub.storage.app.ingredient.entity.Ingredient;

public interface IngredientProvider {

    Ingredient provideIngredient(String name, Long amount);
}
