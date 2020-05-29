package by.pub.storage.app.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StorageEventPublisherImpl implements StorageEventPublisher {
    private final ApplicationEventPublisher publisher;

    public StorageEventPublisherImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publishEvent(Object event) {
        publisher.publishEvent(event);
    }
}
