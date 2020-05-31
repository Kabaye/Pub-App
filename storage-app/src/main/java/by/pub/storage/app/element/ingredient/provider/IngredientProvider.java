package by.pub.storage.app.element.ingredient.provider;

import by.pub.storage.app.element.ingredient.entity.Ingredient;

public interface IngredientProvider {
    Ingredient provideIngredient(String name, Long amount);
}
