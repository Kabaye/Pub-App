package by.pub.storage.app.ingredient.publisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StorageEventPublisherImpl implements StorageEventPublisher {
    private final ApplicationEventPublisher publisher;

    public StorageEventPublisherImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publishEvent(ApplicationEvent applicationEvent) {
        System.out.println("event published: " + applicationEvent);
        publisher.publishEvent(applicationEvent);
    }
}
