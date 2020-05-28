package by.pub.storage.app.ingredient.repository;

import by.pub.storage.app.ingredient.entity.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    Optional<Ingredient> findByName(String name);

    void deleteByName(String name);
}
