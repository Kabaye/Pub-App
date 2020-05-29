package by.pub.storage.app.event.publisher;

import by.pub.storage.app.event.entity.StorageAppEvent;

public interface StorageEventPublisher {
    void publishEvent(StorageAppEvent storageAppEvent);
}
