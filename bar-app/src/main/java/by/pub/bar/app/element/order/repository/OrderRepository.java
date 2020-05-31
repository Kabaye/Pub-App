package by.pub.bar.app.element.order.repository;

import by.pub.bar.app.element.order.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
