package by.pub.bar.app.element.order.entity;

import by.pub.bar.app.element.product.entity.Product;
import by.pub.bar.app.utils.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @Transient
    private Status status = Status.NOT_ACCEPTED;
}
