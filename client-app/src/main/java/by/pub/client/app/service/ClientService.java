package by.pub.client.app.service;

import by.pub.client.app.order.entity.Order;
import by.pub.client.app.product.entity.Product;
import java.util.List;

public interface ClientService {

    List<Product> findAllProducts();

    Order requestOrder(String clientId, List<Product> products);

    void handleAcceptedOrder(Order order);

    String createUniqueID();
}
