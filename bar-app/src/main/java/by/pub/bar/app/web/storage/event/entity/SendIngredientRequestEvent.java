package by.pub.bar.app.web.storage.event.entity;

import by.pub.bar.app.element.ingredient_request.entity.IngredientRequest;
import by.pub.bar.app.event.entity.BarAppEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SendIngredientRequestEvent extends BarAppEvent {

    private IngredientRequest ingredientRequest;
}
