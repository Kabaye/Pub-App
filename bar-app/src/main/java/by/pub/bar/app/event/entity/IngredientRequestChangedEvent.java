package by.pub.bar.app.event.entity;

import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class IngredientRequestChangedEvent extends BarAppEvent {
    private IngredientRequest ingredientRequest;
}
