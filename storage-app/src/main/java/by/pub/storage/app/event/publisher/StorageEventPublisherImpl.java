package by.pub.storage.app.event.publisher;

import by.pub.storage.app.event.entity.StorageAppEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StorageEventPublisherImpl implements StorageEventPublisher {

    private final ApplicationEventPublisher publisher;

    public StorageEventPublisherImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publishEvent(StorageAppEvent event) {
        publisher.publishEvent(event);
    }
}
