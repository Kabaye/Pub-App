package by.pub.storage.app.event.aspect;

import by.pub.storage.app.event.annotation.EventPublishingType;
import by.pub.storage.app.event.entity.IngredientChangedEvent;
import by.pub.storage.app.event.entity.NewIngredientRequestEvent;
import by.pub.storage.app.event.entity.StorageAppEventType;
import by.pub.storage.app.event.publisher.StorageEventPublisher;
import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Aspect
@Component
public class StorageAppEventAspect {
    private final StorageEventPublisher eventPublisher;
    private final Map<StorageAppEventType, Consumer<Object>> eventHandlers;

    public StorageAppEventAspect(StorageEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.eventHandlers = new HashMap<>();

        eventHandlers.put(StorageAppEventType.INGREDIENT_CHANGED_EVENT, val -> eventPublisher.publishEvent(new IngredientChangedEvent(((Ingredient) val))));
        eventHandlers.put(StorageAppEventType.NEW_INGREDIENT_REQUEST_EVENT, val -> eventPublisher.publishEvent(new NewIngredientRequestEvent(((IngredientRequest) val))));
    }

    @SneakyThrows
    @Around("@annotation(by.pub.storage.app.event.annotation.EventPublishingType)")
    public Object after(ProceedingJoinPoint joinPoint) {
        final EventPublishingType eventType = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(EventPublishingType.class);
        Object result = joinPoint.proceed();
        eventHandlers.get(eventType.value()).accept(result);
        return result;
    }


}
