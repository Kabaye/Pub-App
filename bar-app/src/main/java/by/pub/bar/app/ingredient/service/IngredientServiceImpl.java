package by.pub.bar.app.ingredient.service;

import by.pub.bar.app.event.entity.IngredientChangedEvent;
import by.pub.bar.app.event.publisher.BarEventPublisher;
import by.pub.bar.app.ingredient.entity.Ingredient;
import by.pub.bar.app.ingredient.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final BarEventPublisher publisher;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, BarEventPublisher publisher) {
        this.ingredientRepository = ingredientRepository;
        this.publisher = publisher;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
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
    public Ingredient takeIngredientsFromBarStand(String ingredientName, Long amount) {
        Ingredient ingredient = findIngredientByName(ingredientName);

        if (amount > ingredient.getAmount()) {
            throw new RuntimeException("There is no enough ingredients on B. Request some from provider!");
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
