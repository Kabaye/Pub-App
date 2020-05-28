package by.pub.storage.app.ingredient.publisher;

import org.springframework.context.ApplicationEvent;

public interface StorageEventPublisher {
    void publishEvent(ApplicationEvent applicationEvent);
}
