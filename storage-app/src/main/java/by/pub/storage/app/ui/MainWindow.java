package by.pub.storage.app.ui;

import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient.event.IngredientChangedEvent;
import by.pub.storage.app.ingredient.event.NewIngredientEvent;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

@Component
public class MainWindow extends JFrame {

    //TODO 28.05.2020 Никаких public констант!

    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;
    private final JPanel mainPanel;
    private final JPanel authPanel;
    private final JPanel ingredientPanel;
    private final JPanel ingredientRequestPanel;
    private final JList<Ingredient> ingredientJList;
    private final DefaultListModel<Ingredient> ingredientDefaultListModel;
    private final JList<IngredientRequest> ingredientRequestJList;
    private final JButton fulfillButton;
    private final JButton ingredientRequestButton;

    //TODO 28.05.2020 Ниже

    /**
     * Жека вот тут все разнеси аккуратно.
     * Лучше пускай будет вызов метода, но будет читабельно, чем вот такая каша
     * Имею в виду, что лучше пускай будет так:
     * <p>
     * initializeComponents() <- тут все создаешь
     * configureComponents() <- тут их конфигуришь
     * <p>
     * и т. д.
     */

    public MainWindow() {
        authPanel = new JPanel();

        mainPanel = new JPanel(new BorderLayout());

        ingredientRequestPanel = new JPanel(new BorderLayout());
        ingredientRequestJList = new JList<>();
        fulfillButton = new JButton("Fulfill request");
        ingredientRequestPanel.add(ingredientRequestJList, BorderLayout.NORTH);
        ingredientRequestJList.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, 2 * SCREEN_HEIGHT / 3));
        ingredientRequestPanel.add(fulfillButton, BorderLayout.SOUTH);
        fulfillButton.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 6));

        ingredientPanel = new JPanel(new BorderLayout());

        ingredientDefaultListModel = new DefaultListModel<>();
        ingredientJList = new JList<>(ingredientDefaultListModel);

        ingredientRequestButton = new JButton("Request more");
        ingredientPanel.add(ingredientJList, BorderLayout.NORTH);
        ingredientJList.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, 2 * SCREEN_HEIGHT / 3));
        ingredientPanel.add(ingredientRequestButton, BorderLayout.SOUTH);
        ingredientRequestButton.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 6));

        mainPanel.add(ingredientJList, BorderLayout.EAST);
        mainPanel.add(ingredientRequestJList, BorderLayout.WEST);

        setMainWindowPreferences();
    }

    private void setMainWindowPreferences() {
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }

    @EventListener
    public void handleIngrChanged(IngredientChangedEvent event) {
        System.out.println(Thread.currentThread().getName());
        ingredientDefaultListModel.addElement(event.getChangedIngredient());
        System.out.println("IngredientChangedEvent: " + event.getChangedIngredient());
    }

    @EventListener
    public void handleNewIngr(NewIngredientEvent event) {
        System.out.println(Thread.currentThread().getName());
        ingredientDefaultListModel.addElement(event.getNewIngredient());
        System.out.println("NewIngredientEvent: " + event.getNewIngredient());
    }
}
