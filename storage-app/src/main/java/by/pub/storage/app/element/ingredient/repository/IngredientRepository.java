package by.pub.storage.app.element.ingredient.repository;

import by.pub.storage.app.element.ingredient.entity.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    Optional<Ingredient> findByName(String name);

    void deleteByName(String name);
}
