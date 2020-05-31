package by.pub.bar.app.event.entity;

import by.pub.bar.app.element.ingredient.entity.Ingredient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IngredientChangedEvent extends BarAppEvent {
    private final Ingredient ingredient;
}
