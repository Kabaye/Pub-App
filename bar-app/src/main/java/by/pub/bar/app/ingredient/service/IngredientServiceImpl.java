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
    public Ingredient takeIngredientFromBarStand(Ingredient ingredient) {
        Ingredient ingredientInStand = findIngredientByName(ingredient.getName());

        if (ingredient.getAmount() > ingredientInStand.getAmount()) {
            throw new RuntimeException("There is no enough ingredients on Bar stand. Request some from provider!");
        }

        if (ingredient.getAmount().equals(ingredientInStand.getAmount())) {
            deleteIngredientByName(ingredient.getName());
            publisher.publishEvent(new IngredientChangedEvent(ingredientInStand.setAmount(0L)));
            return Ingredient.of(ingredientInStand).setAmount(ingredient.getAmount());
        }

        Ingredient updatedIngredient = ingredientRepository.save(ingredientInStand.setAmount(ingredientInStand.getAmount() - ingredient.getAmount()));
        publisher.publishEvent(new IngredientChangedEvent(updatedIngredient));
        return Ingredient.of(updatedIngredient).setAmount(ingredient.getAmount());
    }

    @Override
    public boolean checkForAvailability(Ingredient ingredient) {
        return ingredientRepository.findAmountOfIngredientByName(ingredient.getName())
                .orElseThrow(() -> new RuntimeException("There is no ingredient with name: " + ingredient.getName()))
                .getAmount() >= ingredient.getAmount();
    }

    @Override
    public Ingredient putIngredientOnBarStand(Ingredient ingredient) {
        Ingredient foundIngredient = findIngredientByName(ingredient.getName());
        foundIngredient.setAmount(ingredient.getAmount() + foundIngredient.getAmount());
        return foundIngredient;
    }
}
