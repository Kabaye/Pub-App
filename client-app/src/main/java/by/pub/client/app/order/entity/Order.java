package by.pub.client.app.order.entity;

import by.pub.client.app.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Order {
    private List<Product> products;
    private String clientId;
    private Double totalPrice = 0D;
    private Status status = Status.NOT_ACCEPTED;
}
