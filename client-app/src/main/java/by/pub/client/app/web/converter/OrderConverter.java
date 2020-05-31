package by.pub.client.app.web.converter;

import by.pub.client.app.order.entity.Order;
import by.pub.client.app.order.entity.Status;
import by.pub.client.app.web.dto.OrderDTO;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    private final ProductConverter productConverter;

    public OrderConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public Order toEntity(OrderDTO orderDTO) {
        return new Order().setClientId(orderDTO.getClientId())
            .setProducts(orderDTO.getProducts().stream()
                .map(productConverter::toEntity)
                .collect(Collectors.toList()))
            .setStatus(Status.valueOf(orderDTO.getStatus()))
            .setTotalPrice(orderDTO.getTotalPrice())
            .setOrderId(orderDTO.getOrderId());
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO().setClientId(order.getClientId())
            .setProducts(order.getProducts().stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList()))
            .setOrderId(order.getOrderId());
    }
}
