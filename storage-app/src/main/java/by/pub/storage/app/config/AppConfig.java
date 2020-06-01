package by.pub.storage.app.config;

import by.pub.storage.app.element.ingredient.provider.IngredientProvider;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executors;

@Configuration
public class AppConfig {
    private static final String APP_VERSION = "v1.3.5";

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

    @Bean
    public String appVersion() {
        return APP_VERSION;
    }
}
