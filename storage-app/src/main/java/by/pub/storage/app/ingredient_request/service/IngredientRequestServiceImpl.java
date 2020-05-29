package by.pub.storage.app.ingredient_request.service;

import by.pub.storage.app.ingredient.service.IngredientService;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import by.pub.storage.app.ingredient_request.repository.IngredientRequestRepository;
import by.pub.storage.app.websocket.web_client.IngredientRequestWebClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientRequestServiceImpl implements IngredientRequestService {
    private final IngredientRequestRepository ingredientRequestRepository;
    private final IngredientService ingredientService;
    private final IngredientRequestWebClient ingredientRequestWebClient;
    private final StorageEventPublisher publisher;

    public IngredientRequestServiceImpl(IngredientRequestRepository ingredientRequestRepository, IngredientService ingredientService, IngredientRequestWebClient ingredientRequestWebClient, StorageEventPublisher publisher) {
        this.ingredientRequestRepository = ingredientRequestRepository;
        this.ingredientService = ingredientService;
        this.ingredientRequestWebClient = ingredientRequestWebClient;
        this.publisher = publisher;
    }

    @Override
    public IngredientRequest findByRequestId(String requestId) {
        return ingredientRequestRepository.findByRequestId(requestId)
                .orElseThrow(() -> new RuntimeException("There is no Ingredient request with request id: " + requestId));
    }

    @Override
    public List<IngredientRequest> findAllIngredientRequests() {
        return ingredientRequestRepository.findAll();
    }

    @Override
    public IngredientRequest saveIngredientRequest(IngredientRequest ingredientRequest) {
        final IngredientRequest savedIngredientRequest = ingredientRequestRepository.save(ingredientRequest);
        publisher.publishEvent(new NewIngredientRequestEvent(ingredientRequest));
        return savedIngredientRequest;
    }

    @Override
    public IngredientRequest acceptIngredientRequest(IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientService.takeIngredientsFromStorage(ingredientRequest.getIngredientName(), ingredientRequest.getIngredientAmount());
        ingredientRequestRestTemplate.acceptIngredientRequest(ingredientRequest);
        publisher.publishEvent(new IngredientChangedEvent(ingredient));
        return ingredientRequest.setStatus(IngredientRequestStatus.ACCEPTED);
    }

    @Override
    public void deleteByRequestId(String requestId) {
        ingredientRequestRepository.deleteByRequestId(requestId);
    }
}
