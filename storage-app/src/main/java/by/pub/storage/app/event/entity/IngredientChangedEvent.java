package by.pub.storage.app.event.entity;

import by.pub.storage.app.element.ingredient.entity.Ingredient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IngredientChangedEvent extends StorageAppEvent {
    private final Ingredient ingredient;
}
