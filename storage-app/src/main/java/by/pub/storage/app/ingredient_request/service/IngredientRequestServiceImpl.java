package by.pub.storage.app.ingredient_request.service;

import by.pub.storage.app.event.annotation.EventPublishingType;
import by.pub.storage.app.ingredient.service.IngredientService;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import by.pub.storage.app.ingredient_request.repository.IngredientRequestRepository;
import by.pub.storage.app.websocket.web_client.IngredientRequestWebClient;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.pub.storage.app.event.entity.StorageAppEventType.INGREDIENT_CHANGED_EVENT;
import static by.pub.storage.app.event.entity.StorageAppEventType.NEW_INGREDIENT_REQUEST_EVENT;

@Service
public class IngredientRequestServiceImpl implements IngredientRequestService {
    private final IngredientRequestRepository ingredientRequestRepository;
    private final IngredientService ingredientService;
    private final IngredientRequestWebClient ingredientRequestWebClient;

    public IngredientRequestServiceImpl(IngredientRequestRepository ingredientRequestRepository, IngredientService ingredientService, IngredientRequestWebClient ingredientRequestWebClient) {
        this.ingredientRequestRepository = ingredientRequestRepository;
        this.ingredientService = ingredientService;
        this.ingredientRequestWebClient = ingredientRequestWebClient;
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
    @EventPublishingType(NEW_INGREDIENT_REQUEST_EVENT)
    public IngredientRequest saveIngredientRequest(IngredientRequest ingredientRequest) {
        return ingredientRequestRepository.save(ingredientRequest);
    }

    @Override
    @EventPublishingType(INGREDIENT_CHANGED_EVENT)
    public IngredientRequest acceptIngredientRequest(IngredientRequest ingredientRequest) {
        ingredientService.takeIngredientsFromStorage(ingredientRequest.getIngredientName(), ingredientRequest.getIngredientAmount());
        ingredientRequestWebClient.acceptIngredientRequest(ingredientRequest);
        return ingredientRequest.setStatus(IngredientRequestStatus.ACCEPTED);
    }

    @Override
    public void deleteByRequestId(String requestId) {
        ingredientRequestRepository.deleteByRequestId(requestId);
    }
}
