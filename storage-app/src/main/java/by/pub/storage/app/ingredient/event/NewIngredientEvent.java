package by.pub.storage.app.ingredient.event;

import by.pub.storage.app.ingredient.entity.Ingredient;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewIngredientEvent extends ApplicationEvent {
    private final Ingredient newIngredient;

    public NewIngredientEvent(Object source, Ingredient newIngredient) {
        super(source);
        this.newIngredient = newIngredient;
    }
}
