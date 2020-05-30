package by.pub.bar.app.product.repository;

import by.pub.bar.app.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);

    void deleteByName(String name);
}
