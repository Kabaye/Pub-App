package by.pub.bar.app.web.client.converter;

import by.pub.bar.app.element.order.entity.Order;
import by.pub.bar.app.web.client.dto.OrderDTO;
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
            .setTotalPrice(orderDTO.getTotalPrice())
            .setId(orderDTO.getOrderId());

    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO().setClientId(order.getClientId())
            .setProducts(order.getProducts().stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList()))
            .setStatus(order.getStatus().name())
            .setTotalPrice(order.getTotalPrice())
            .setOrderId(order.getId());
    }
}
