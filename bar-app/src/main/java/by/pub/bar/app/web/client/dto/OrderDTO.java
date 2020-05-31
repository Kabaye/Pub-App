package by.pub.bar.app.web.client.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDTO {
    private List<ProductDTO> products;
    private String clientId;
    private Double totalPrice = 0D;
    private String status;
}
