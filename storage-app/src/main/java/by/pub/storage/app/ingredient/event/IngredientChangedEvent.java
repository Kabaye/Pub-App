package by.pub.storage.app.ingredient.event;

import by.pub.storage.app.ingredient.entity.Ingredient;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class IngredientChangedEvent extends ApplicationEvent {
    private final Ingredient changedIngredient;

    public IngredientChangedEvent(Object source, Ingredient changedIngredient) {
        super(source);
        this.changedIngredient = changedIngredient;
    }
}
