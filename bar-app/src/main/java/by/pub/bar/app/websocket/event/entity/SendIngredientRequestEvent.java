package by.pub.bar.app.websocket.event.entity;

import by.pub.bar.app.event.entity.BarAppEvent;
import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SendIngredientRequestEvent extends BarAppEvent {
    private IngredientRequest ingredientRequest;
}
