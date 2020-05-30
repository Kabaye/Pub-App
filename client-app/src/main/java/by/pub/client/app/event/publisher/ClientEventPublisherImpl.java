package by.pub.client.app.event.publisher;

import by.pub.client.app.event.entity.ClientAppEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ClientEventPublisherImpl implements ClientEventPublisher {
    private final ApplicationEventPublisher publisher;

    public ClientEventPublisherImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publishEvent(ClientAppEvent event) {
        publisher.publishEvent(event);
    }
}
