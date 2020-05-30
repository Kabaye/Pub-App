package by.pub.bar.app.ingredient.repository;

import by.pub.bar.app.ingredient.entity.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    Optional<Ingredient> findByName(String name);

    void deleteByName(String name);

    @Query(fields = "{amount : 1}")
    Optional<Ingredient> findAmountOfIngredientByName(String name);
}
