package by.pub.storage.app.event.annotation;

import by.pub.storage.app.event.entity.StorageAppEventType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EventPublishingType {
    StorageAppEventType value() default StorageAppEventType.NEW_INGREDIENT_REQUEST_EVENT;
}
