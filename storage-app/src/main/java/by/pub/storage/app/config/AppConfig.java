package by.pub.storage.app.config;

import by.pub.storage.app.element.ingredient.provider.IngredientProvider;
import java.util.concurrent.Executors;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
public class AppConfig {

    @Bean
    public IngredientProvider ingredientProvider() {
        return Mockito.mock(IngredientProvider.class);
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        final SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(Executors.newCachedThreadPool());
        return simpleApplicationEventMulticaster;
    }
}
