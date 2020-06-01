package by.pub.bar.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    private static final String APP_VERSION = "v1.3.9";

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(Executors.newCachedThreadPool());
        return simpleApplicationEventMulticaster;
    }

    @Bean
    public String appVersion() {
        return APP_VERSION;
    }
}
