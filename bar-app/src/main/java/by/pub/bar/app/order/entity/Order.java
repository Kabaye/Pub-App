package by.pub.bar.app.order.entity;

import by.pub.bar.app.product.entity.Product;
import by.pub.bar.app.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private List<Product> products;
    private String clientId;
    private Double totalPrice = 0D;
    private Status status = Status.NOT_ACCEPTED;
}
