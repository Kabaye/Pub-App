package by.pub.bar.app.element.ingredient.service;

import by.pub.bar.app.element.ingredient.entity.Ingredient;
import by.pub.bar.app.element.ingredient.repository.IngredientRepository;
import by.pub.bar.app.event.entity.IngredientChangedEvent;
import by.pub.bar.app.event.publisher.BarEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Ingredient> foundIngredientOpt = ingredientRepository
            .findByName(ingredient.getName());
        if (foundIngredientOpt.isEmpty()) {
            Ingredient savedIngredient = saveIngredient(ingredient);
            publisher.publishEvent(new IngredientChangedEvent(savedIngredient));
            return savedIngredient;
        }
        Ingredient foundIngredient = foundIngredientOpt.get();
        foundIngredient.setAmount(ingredient.getAmount() + foundIngredient.getAmount());
        publisher.publishEvent(new IngredientChangedEvent(foundIngredient));
        return foundIngredient;
    }
}
