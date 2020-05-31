package by.pub.bar.app.web.client.converter;

import by.pub.bar.app.element.product.entity.Product;
import by.pub.bar.app.web.client.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product toEntity(ProductDTO productDTO) {
        return new Product().setName(productDTO.getName())
            .setPrice(productDTO.getPrice());
    }

    public ProductDTO toDTO(Product product) {
        return new ProductDTO().setName(product.getName())
            .setPrice(product.getPrice());
    }
}
