package by.pub.storage.app.config;

import by.pub.storage.app.ingredient.provider.IngredientProvider;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public IngredientProvider ingredientProvider(){
        return Mockito.mock(IngredientProvider.class);
    }
}
