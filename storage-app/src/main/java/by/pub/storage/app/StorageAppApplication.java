package by.pub.storage.app;

import by.pub.storage.app.ui.MainWindow;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.awt.EventQueue;

@SpringBootApplication
@EnableAspectJAutoProxy
public class StorageAppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(StorageAppApplication.class)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> {
            MainWindow mainWindow = context.getBean(MainWindow.class);
            mainWindow.setVisible(true);
        });
    }
}
