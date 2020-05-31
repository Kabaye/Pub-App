package by.pub.bar.app.element.product.repository;

import by.pub.bar.app.element.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);
}
