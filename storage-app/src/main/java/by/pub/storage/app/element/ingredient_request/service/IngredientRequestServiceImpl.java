package by.pub.storage.app.element.ingredient_request.service;

import by.pub.storage.app.element.ingredient.service.IngredientService;
import by.pub.storage.app.element.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.element.ingredient_request.entity.IngredientRequestStatus;
import by.pub.storage.app.element.ingredient_request.repository.IngredientRequestRepository;
import by.pub.storage.app.event.entity.NewIngredientRequestEvent;
import by.pub.storage.app.event.publisher.StorageEventPublisher;
import by.pub.storage.app.websocket.message_sender.IngredientRequestWebSocketMessageSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientRequestServiceImpl implements IngredientRequestService {
    private final IngredientRequestRepository ingredientRequestRepository;
    private final IngredientService ingredientService;
    private final IngredientRequestWebSocketMessageSender ingredientRequestWebSocketMessageSender;
    private final StorageEventPublisher publisher;

    public IngredientRequestServiceImpl(IngredientRequestRepository ingredientRequestRepository, IngredientService ingredientService, IngredientRequestWebSocketMessageSender ingredientRequestWebSocketMessageSender, StorageEventPublisher publisher) {
        this.ingredientRequestRepository = ingredientRequestRepository;
        this.ingredientService = ingredientService;
        this.ingredientRequestWebSocketMessageSender = ingredientRequestWebSocketMessageSender;
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
        ingredientService.takeIngredientsFromStorage(ingredientRequest.getIngredientName(), ingredientRequest.getIngredientAmount());
        ingredientRequestWebSocketMessageSender.sendAcceptedIngredientRequest(ingredientRequest.setStatus(IngredientRequestStatus.ACCEPTED));
        deleteByRequestId(ingredientRequest.getRequestId());
        return ingredientRequest;
    }

    @Override
    public void deleteByRequestId(String requestId) {
        ingredientRequestRepository.deleteByRequestId(requestId);
    }
}
