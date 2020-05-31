package by.pub.bar.app.element.product.repository;

import by.pub.bar.app.element.product.entity.Product;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByName(String name);
}
