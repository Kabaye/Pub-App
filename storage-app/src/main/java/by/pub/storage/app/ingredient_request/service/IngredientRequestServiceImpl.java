package by.pub.storage.app.ingredient_request.service;

import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient.service.IngredientService;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import by.pub.storage.app.ingredient_request.repository.IngredientRequestRepository;
import by.pub.storage.app.ingredient_request.web_client.IngredientRequestRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientRequestServiceImpl implements IngredientRequestService {
    private final IngredientRequestRepository ingredientRequestRepository;
    private final IngredientService ingredientService;
    private final IngredientRequestRestTemplate ingredientRequestRestTemplate;

    public IngredientRequestServiceImpl(IngredientRequestRepository ingredientRequestRepository, IngredientService ingredientService, IngredientRequestRestTemplate ingredientRequestRestTemplate) {
        this.ingredientRequestRepository = ingredientRequestRepository;
        this.ingredientService = ingredientService;
        this.ingredientRequestRestTemplate = ingredientRequestRestTemplate;
    }

    @Override
    public IngredientRequest findByRequestId(String requestId) {
        return ingredientRequestRepository.findByRequestId(requestId)
                .orElseThrow(() -> new RuntimeException("There is no Ingredient request with request id: " + requestId));
    }

    @Override
    public List<IngredientRequest> findAll() {
        return ingredientRequestRepository.findAll();
    }

    @Override
    public IngredientRequest saveIngredientRequest(IngredientRequest ingredientRequest) {
        return ingredientRequestRepository.save(ingredientRequest);
    }

    @Override
    public IngredientRequest acceptIngredientRequest(IngredientRequest ingredientRequest) {
        ingredientService.updateIngredientAmount(ingredientRequest.getIngredientName(), ingredientRequest.getIngredientAmount());
        ingredientRequestRestTemplate.acceptIngredientRequest(ingredientRequest);
        return ingredientRequest.setStatus(IngredientRequestStatus.ACCEPTED);
    }

    @Override
    public void deleteByRequestId(String requestId) {
        ingredientRequestRepository.deleteByRequestId(requestId);
    }
}
