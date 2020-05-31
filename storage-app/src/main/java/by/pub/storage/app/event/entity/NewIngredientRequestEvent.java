package by.pub.storage.app.event.entity;

import by.pub.storage.app.element.ingredient_request.entity.IngredientRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NewIngredientRequestEvent extends StorageAppEvent {

    private final IngredientRequest ingredientRequest;

}
