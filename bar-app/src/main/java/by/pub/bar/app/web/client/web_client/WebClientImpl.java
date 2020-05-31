package by.pub.bar.app.web.client.web_client;

import by.pub.bar.app.element.order.entity.Order;
import by.pub.bar.app.web.client.converter.OrderConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebClientImpl implements WebClient {
    private final RestTemplate restTemplate;
    private final OrderConverter orderConverter;

    public WebClientImpl(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
        this.restTemplate = new RestTemplate();
    }

    //TODO 31.05.2020 send in new thread. check why sent is good, received is null on client side.
    @Override
    public void sendAcceptedOrder(Order order) {
       /* restTemplate.exchange(
                "http://localhost:9892/api/v1/clients/accept-order",
                HttpMethod.POST,
                new HttpEntity<>(orderConverter.toDTO(order)),
                new ParameterizedTypeReference<>() {
                });*/
    }
}
