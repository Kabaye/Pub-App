package by.pub.bar.app.event.publisher;

import by.pub.bar.app.event.entity.BarAppEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BarEventPublisherImpl implements BarEventPublisher {
    private final ApplicationEventPublisher publisher;

    public BarEventPublisherImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publishEvent(BarAppEvent event) {
        publisher.publishEvent(event);
    }
}
