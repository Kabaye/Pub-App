package by.pub.bar.app.web.storage.event.listener;

import by.pub.bar.app.element.ingredient.entity.Ingredient;
import by.pub.bar.app.element.ingredient.service.IngredientService;
import by.pub.bar.app.element.ingredient_request.service.IngredientRequestService;
import by.pub.bar.app.web.storage.event.entity.ReceiveAcceptedIngredientRequestEvent;
import by.pub.bar.app.web.storage.event.entity.SendIngredientRequestEvent;
import by.pub.bar.app.web.storage.handler.MyStompSessionHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DefaultEventListener {
    private final IngredientRequestService ingredientRequestService;
    private final IngredientService ingredientService;
    private final MyStompSessionHandler sessionHandler;

    public DefaultEventListener(IngredientRequestService ingredientRequestService,
        IngredientService ingredientService, MyStompSessionHandler sessionHandler) {
        this.ingredientRequestService = ingredientRequestService;
        this.ingredientService = ingredientService;
        this.sessionHandler = sessionHandler;
    }

    @EventListener
    public void handleAcceptedIngredientRequest(ReceiveAcceptedIngredientRequestEvent event) {
        ingredientRequestService.deleteByRequestId(event.getIngredientRequest().getRequestId());
        Ingredient ingredient = new Ingredient().setAmount(event.getIngredientRequest().getIngredientAmount())
                .setName(event.getIngredientRequest().getIngredientName());
        ingredientService.putIngredientOnBarStand(ingredient);
    }

    @EventListener
    public void sendIngredientRequest(SendIngredientRequestEvent ingredientRequestEvent) {
        sessionHandler.sendData("/storage-app/request-ingredient", ingredientRequestEvent.getIngredientRequest());
    }
}
