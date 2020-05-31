package by.pub.client.app.order.entity;

import by.pub.client.app.product.entity.Product;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Order {

    private String orderId = UUID.randomUUID().toString();
    private List<Product> products;
    private String clientId;
    private Double totalPrice = 0D;
    private Status status = Status.NOT_ACCEPTED;
}
