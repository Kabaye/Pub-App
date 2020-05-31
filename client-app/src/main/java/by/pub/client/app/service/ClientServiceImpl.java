package by.pub.client.app.service;

import by.pub.client.app.event.entity.ReceivedAcceptedOrderEvent;
import by.pub.client.app.event.publisher.ClientEventPublisher;
import by.pub.client.app.order.entity.Order;
import by.pub.client.app.product.entity.Product;
import by.pub.client.app.web.converter.OrderConverter;
import by.pub.client.app.web.converter.ProductConverter;
import by.pub.client.app.web.web_client.WebClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final WebClient webClient;
    private final OrderConverter orderConverter;
    private final ProductConverter productConverter;
    private final ClientEventPublisher publisher;

    public ClientServiceImpl(WebClient webClient, OrderConverter orderConverter, ProductConverter productConverter, ClientEventPublisher publisher) {
        this.webClient = webClient;
        this.orderConverter = orderConverter;
        this.productConverter = productConverter;
        this.publisher = publisher;
    }

    public List<Product> findAllProducts() {
        return webClient.findAllProducts()
                .stream()
                .map(productConverter::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Order requestOrder(String clientId, List<Product> products) {
        return orderConverter.toEntity(webClient.createOrder(orderConverter.toDTO(new Order().setProducts(products)
                .setClientId(clientId))));
    }

    @Override
    public void handleAcceptedOrder(Order order) {
        System.out.println(order);
        publisher.publishEvent(new ReceivedAcceptedOrderEvent().setOrder(order));
    }

    @Override
    public String createUniqueID() {
        return UUID.randomUUID().toString();
    }
}
