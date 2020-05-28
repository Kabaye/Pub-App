package by.pub.storage.app.ingredient.service;

import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient.event.IngredientChangedEvent;
import by.pub.storage.app.ingredient.event.NewIngredientEvent;
import by.pub.storage.app.ingredient.provider.IngredientProvider;
import by.pub.storage.app.ingredient.publisher.StorageEventPublisher;
import by.pub.storage.app.ingredient.repository.IngredientRepository;
import lombok.SneakyThrows;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientProvider ingredientProvider;
    private final StorageEventPublisher storageEventPublisher;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientProvider ingredientProvider, StorageEventPublisher storageEventPublisher) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientProvider = ingredientProvider;
        this.storageEventPublisher = storageEventPublisher;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    @SneakyThrows
    //TODO 27.05.2020 Check this
    public Ingredient orderIngredient(String name, Long amount) {
        Mockito.when(ingredientProvider.provideIngredient(name, amount))
                .thenReturn(new Ingredient().setAmount(amount)
                        .setName(name));

        Ingredient ingredientInDb = findIngredientByName(name);

        // Imitation of work
        TimeUnit.MILLISECONDS.sleep(100);
        Ingredient orderedIngredient = ingredientProvider.provideIngredient(name, amount);

        final Ingredient ingredient = saveIngredient(ingredientInDb.setAmount(ingredientInDb.getAmount() + orderedIngredient.getAmount()));

        storageEventPublisher.publishEvent(new IngredientChangedEvent(storageEventPublisher, ingredient));

        return ingredient;
    }

    @Override
    public Ingredient findIngredientByName(String name) {
        return ingredientRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("There is no ingredient with name: " + name));
    }

    @Override
    public Ingredient findIngredientById(String id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no ingredient with id: " + id));
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        storageEventPublisher.publishEvent(new NewIngredientEvent(storageEventPublisher, ingredient));
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteIngredientByName(String name) {
        ingredientRepository.deleteByName(name);
    }

    @Override
    public Ingredient takeIngredients(String ingredientName, Long amount) {
        Ingredient ingredient = findIngredientByName(ingredientName);
        if (amount > ingredient.getAmount()) {
            throw new RuntimeException("There is no enough ingredients on storage. Request some from provider!");
        }
        final Ingredient updatedIngredient = ingredientRepository.save(ingredient.setAmount(ingredient.getAmount() - amount));

        storageEventPublisher.publishEvent(new IngredientChangedEvent(storageEventPublisher, updatedIngredient));

        return ingredient.setAmount(amount);
    }
}
