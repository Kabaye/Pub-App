package by.pub.storage.app.ingredient.repository;

import by.pub.storage.app.ingredient.entity.Ingredient;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    Optional<Ingredient> findByName(String name);

    void deleteByName(String name);
}
