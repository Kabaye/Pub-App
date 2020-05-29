package by.pub.storage.app.ingredient.service;

import by.pub.storage.app.event.entity.IngredientChangedEvent;
import by.pub.storage.app.event.publisher.StorageEventPublisher;
import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient.provider.IngredientProvider;
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
    private final StorageEventPublisher publisher;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 IngredientProvider ingredientProvider, StorageEventPublisher publisher) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientProvider = ingredientProvider;
        this.publisher = publisher;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    @SneakyThrows
    public Ingredient orderIngredient(String name, Long amount) {
        Mockito.when(ingredientProvider.provideIngredient(name, amount))
                .thenReturn(new Ingredient().setAmount(amount)
                        .setName(name));

        Ingredient ingredientInDb = findIngredientByName(name);

        // Imitation of work
        TimeUnit.MILLISECONDS.sleep(100);
        Ingredient orderedIngredient = ingredientProvider.provideIngredient(name, amount);
        return saveIngredient(ingredientInDb.setAmount(ingredientInDb.getAmount() + orderedIngredient.getAmount()));
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
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteIngredientByName(String name) {
        ingredientRepository.deleteByName(name);
    }

    @Override
    public Ingredient takeIngredientsFromStorage(String ingredientName, Long amount) {
        Ingredient ingredient = findIngredientByName(ingredientName);

        if (amount > ingredient.getAmount()) {
            throw new RuntimeException(
                    "There is no enough ingredients on storage. Request some from provider!");
        }

        if (amount.equals(ingredient.getAmount())) {
            deleteIngredientByName(ingredientName);
            publisher.publishEvent(new IngredientChangedEvent(ingredient.setAmount(0L)));
            return Ingredient.of(ingredient).setAmount(amount);
        }

        Ingredient updatedIngredient = ingredientRepository.save(ingredient.setAmount(ingredient.getAmount() - amount));
        publisher.publishEvent(new IngredientChangedEvent(updatedIngredient));
        return Ingredient.of(updatedIngredient).setAmount(amount);
    }
}
