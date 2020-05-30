package by.pub.client.app.web.web_client;

import by.pub.client.app.web.dto.OrderDTO;
import by.pub.client.app.web.dto.ProductDTO;

import java.util.List;

public interface WebClient {
    List<ProductDTO> findAllProducts();

    OrderDTO createOrder(OrderDTO order);
}
