package by.pub.bar.app.websocket.storage_client.event.entity;

import by.pub.bar.app.event.entity.BarAppEvent;
import by.pub.bar.app.ingredient_request.entity.IngredientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReceiveAcceptedIngredientRequestEvent extends BarAppEvent {
    private IngredientRequest ingredientRequest;

}
