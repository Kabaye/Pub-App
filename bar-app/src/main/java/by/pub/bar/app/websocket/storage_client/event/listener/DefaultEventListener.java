package by.pub.bar.app.websocket.storage_client.event.listener;

import by.pub.bar.app.event.entity.IngredientRequestChangedEvent;
import by.pub.bar.app.event.publisher.BarEventPublisher;
import by.pub.bar.app.ingredient_request.service.IngredientRequestService;
import by.pub.bar.app.websocket.storage_client.event.entity.ReceiveAcceptedIngredientRequestEvent;
import by.pub.bar.app.websocket.storage_client.event.entity.SendIngredientRequestEvent;
import by.pub.bar.app.websocket.storage_client.handler.MyStompSessionHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DefaultEventListener {
    private final BarEventPublisher publisher;
    private final IngredientRequestService ingredientRequestService;
    private final MyStompSessionHandler sessionHandler;

    public DefaultEventListener(BarEventPublisher publisher, IngredientRequestService ingredientRequestService, MyStompSessionHandler sessionHandler) {
        this.publisher = publisher;
        this.ingredientRequestService = ingredientRequestService;
        this.sessionHandler = sessionHandler;
    }

    @EventListener
    public void handleAcceptedIngredientRequest(ReceiveAcceptedIngredientRequestEvent event) {
        ingredientRequestService.deleteByRequestId(event.getIngredientRequest().getRequestId());
        publisher.publishEvent(new IngredientRequestChangedEvent(event.getIngredientRequest()));
    }

    @EventListener
    public void sendIngredientRequest(SendIngredientRequestEvent ingredientRequestEvent) {
        sessionHandler.sendData("/storage-app/request-ingredient", ingredientRequestEvent.getIngredientRequest());
    }
}
