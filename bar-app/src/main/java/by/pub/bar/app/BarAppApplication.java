package by.pub.bar.app;

import by.pub.bar.app.ui.frame.MainWindow;
import java.awt.EventQueue;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BarAppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(
            BarAppApplication.class)
            .headless(false)
            .run(args);

        EventQueue.invokeLater(() -> {
            MainWindow mainWindow = context.getBean(MainWindow.class);
            mainWindow.setVisible(true);
        });
    }

}
