package by.pub.bar.app.web.client.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDTO {

    private String name;
    private Double price = 0D;
}
