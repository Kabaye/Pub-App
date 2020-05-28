package by.pub.storage.app.ingredient.service;

import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient.repository.IngredientRepository;
import by.pub.storage.app.provider.IngredientProvider;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientProvider ingredientProvider;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientProvider ingredientProvider) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientProvider = ingredientProvider;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    //TODO 27.05.2020 Check this
    public Ingredient orderIngredients(String name, Long amount) {
        Mockito.when(ingredientProvider.provideIngredient(name, amount))
                .thenReturn(new Ingredient().setAmount(amount)
                        .setName(name));
        return ingredientProvider.provideIngredient(name, amount);
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
}
