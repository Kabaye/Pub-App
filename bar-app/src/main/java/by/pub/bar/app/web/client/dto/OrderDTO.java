package by.pub.bar.app.web.client.dto;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDTO {

    private String orderId;
    private List<ProductDTO> products;
    private String clientId;
    private Double totalPrice = 0D;
    private String status;
}
