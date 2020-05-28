package by.pub.storage.app.ingredient_request.repository;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IngredientRequestRepository extends MongoRepository<IngredientRequest, String> {
    Optional<IngredientRequest> findByRequestId(String requestId);

    void deleteByRequestId(String requestId);
}
