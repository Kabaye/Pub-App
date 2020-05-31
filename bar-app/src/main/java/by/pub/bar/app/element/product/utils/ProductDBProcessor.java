package by.pub.bar.app.element.product.utils;

import by.pub.bar.app.element.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDBProcessor {

    public Product toDB(Product product) {
        long l = (long) (product.getPrice() * 1000);
        return product.setPrice((double) l);
    }

    public Product fromDB(Product product) {
        return product.setPrice((long) (product.getPrice() / 10) / 100D);
    }
}
