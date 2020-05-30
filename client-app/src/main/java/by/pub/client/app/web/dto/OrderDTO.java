package by.pub.client.app.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDTO {
    private List<ProductDTO> productDTOs;
    private String clientId;
    private Double totalPrice = 0D;
    private String status;
}
