package by.pub.client.app.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDTO {
    private String name;
    private Double price = 0D;
}
