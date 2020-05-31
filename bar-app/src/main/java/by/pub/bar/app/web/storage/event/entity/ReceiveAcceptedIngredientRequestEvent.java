package by.pub.bar.app.web.storage.event.entity;

import by.pub.bar.app.element.ingredient_request.entity.IngredientRequest;
import by.pub.bar.app.event.entity.BarAppEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReceiveAcceptedIngredientRequestEvent extends BarAppEvent {

    private IngredientRequest ingredientRequest;

}
