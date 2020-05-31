package by.pub.bar.app.event.publisher;

import by.pub.bar.app.event.entity.BarAppEvent;

public interface BarEventPublisher {

    void publishEvent(BarAppEvent barAppEvent);
}
