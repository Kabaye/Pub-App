package by.pub.storage.app.ingredient_request.repository;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRequestRepository extends MongoRepository<IngredientRequest, String> {

    Optional<IngredientRequest> findByRequestId(String requestId);

    void deleteByRequestId(String requestId);
}
