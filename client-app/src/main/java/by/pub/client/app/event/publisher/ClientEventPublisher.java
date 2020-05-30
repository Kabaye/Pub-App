package by.pub.client.app.event.publisher;


import by.pub.client.app.event.entity.ClientAppEvent;

public interface ClientEventPublisher {
    void publishEvent(ClientAppEvent clientAppEvent);
}
