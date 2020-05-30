package by.pub.bar.app.event.entity;

import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NewIngredientRequestEvent extends BarAppEvent {
    private final IngredientRequest ingredientRequest;

}
