package by.pub.client.app.web.web_client;

import by.pub.client.app.web.dto.OrderDTO;
import by.pub.client.app.web.dto.ProductDTO;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebClientImpl implements WebClient {

    private final RestTemplate restTemplate;

    public WebClientImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
            "http://localhost:9891/api/v1/products",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            });
        return response.getBody();
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return restTemplate
            .postForObject("http://localhost:9891/api/v1/orders", orderDTO, OrderDTO.class);
    }
}
