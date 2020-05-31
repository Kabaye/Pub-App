package by.pub.bar.app.element.ingredient_request.service;

import by.pub.bar.app.element.ingredient_request.entity.IngredientRequest;
import by.pub.bar.app.element.ingredient_request.repository.IngredientRequestRepository;
import by.pub.bar.app.event.publisher.BarEventPublisher;
import by.pub.bar.app.web.storage.event.entity.SendIngredientRequestEvent;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class IngredientRequestServiceImpl implements IngredientRequestService {

    private final IngredientRequestRepository ingredientRequestRepository;
    private final BarEventPublisher publisher;

    public IngredientRequestServiceImpl(IngredientRequestRepository ingredientRequestRepository,
        BarEventPublisher publisher) {
        this.ingredientRequestRepository = ingredientRequestRepository;
        this.publisher = publisher;
    }

    @Override
    public void deleteByRequestId(String requestId) {
        ingredientRequestRepository.deleteByRequestId(requestId);
    }

    @Override
    public IngredientRequest createAndSendIngredientRequest(String ingredientName, Long amount) {
        IngredientRequest ingredientRequest = ingredientRequestRepository
            .save(new IngredientRequest()
                .setIngredientAmount(amount)
                .setIngredientName(ingredientName)
                .setRequestId(UUID.randomUUID().toString()));

        publisher.publishEvent(new SendIngredientRequestEvent(ingredientRequest));
        return ingredientRequest;
    }
}
