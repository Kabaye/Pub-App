package by.pub.bar.app.element.ingredient_request.repository;

import by.pub.bar.app.element.ingredient_request.entity.IngredientRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRequestRepository extends MongoRepository<IngredientRequest, String> {

    void deleteByRequestId(String requestId);
}
