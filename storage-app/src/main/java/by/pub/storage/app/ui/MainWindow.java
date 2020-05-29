package by.pub.storage.app.ui;

import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import org.springframework.stereotype.Component;

@Component
public class MainWindow extends JFrame {

    private final JPanel ingredientPanel;

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 30);
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;

    private final JPanel mainPanel;
    private final JPanel authPanel;
    private final JScrollPane ingredientScrollPane;
    private final JPanel ingredientRequestPanel;
    private final JScrollPane ingredientRequestScrollPane;
    private final ListModel<IngredientRequest> ingredientRequestListModel;
    private final JLabel ingredientLabel;
    private final JLabel ingredientRequestLabel;
    private final ListModel<Ingredient> ingredientListModel;
    private final JButton fulfillButton;
    private final JList<Ingredient> ingredientJList;
    private final JList<IngredientRequest> ingredientRequestJList;
    private final JButton ingredientRequestButton;

    public MainWindow() {
        authPanel = new JPanel();

        mainPanel = new JPanel(new GridLayout(1, 2));

        ingredientRequestPanel = new JPanel(new BorderLayout());
        ingredientRequestListModel = new DefaultListModel<>();
        ingredientRequestJList = new JList<>(ingredientRequestListModel);
        ingredientRequestScrollPane = new JScrollPane(ingredientRequestJList);
        fulfillButton = new JButton("Fulfill request");
        ingredientRequestLabel = new JLabel("Requests from bartender");

        ingredientPanel = new JPanel(new BorderLayout());
        ingredientListModel = new DefaultListModel<>();
        ingredientJList = new JList<>(ingredientListModel);
        ingredientScrollPane = new JScrollPane(ingredientJList);
        ingredientRequestButton = new JButton("Request ingredients");
        ingredientLabel = new JLabel("Available ingredients");

        addListeners();
        addComponentsToIngredientPanel();
        addComponentsToIngredientRequestPanel();
        addComponentsToMainPanel();
        configureComponents();
        setWindowPreferences();

        addIngredientRequest(new IngredientRequest());
        addIngredientRequest(new IngredientRequest());
        addIngredientRequest(new IngredientRequest());
        addIngredientRequest(new IngredientRequest());
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }

    private void addListeners() {
        fulfillButton.addActionListener(e -> {
            ListSelectionModel selectionModel = ingredientRequestJList.getSelectionModel();
            int index = selectionModel.getMinSelectionIndex();
            if (index >= 0) {
                removeIngredientRequest(index);
            }
        });
        ingredientRequestButton.addActionListener(e -> {
            JDialog dialog = new RequestProviderDialog(MainWindow.this);
            dialog.setVisible(true);
        });
    }

    private void addComponentsToIngredientPanel() {
        ingredientPanel.add(ingredientLabel, BorderLayout.NORTH);
        ingredientPanel.add(ingredientScrollPane, BorderLayout.CENTER);
        ingredientPanel.add(ingredientRequestButton, BorderLayout.SOUTH);
    }

    private void addComponentsToIngredientRequestPanel() {
        ingredientRequestPanel.add(ingredientRequestLabel, BorderLayout.NORTH);
        ingredientRequestPanel.add(ingredientRequestScrollPane, BorderLayout.CENTER);
        ingredientRequestPanel.add(fulfillButton, BorderLayout.SOUTH);
    }

    private void addComponentsToMainPanel() {
        mainPanel.add(ingredientRequestPanel);
        mainPanel.add(ingredientPanel);
    }

    private void configureComponents() {
        //panels configuration
        ingredientRequestPanel.setBorder(BorderFactory.createBevelBorder(1));
        ingredientPanel.setBorder(BorderFactory.createBevelBorder(1));
        //jLists configuration
        ingredientRequestJList.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
        ingredientJList.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
        ingredientRequestJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //labels configuration
        ingredientLabel.setFont(HEADER_FONT);
        ingredientLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingredientRequestLabel.setFont(HEADER_FONT);
        ingredientRequestLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //buttons configuration
        fulfillButton.setFont(HEADER_FONT);
        ingredientRequestButton.setFont(HEADER_FONT);

    }

    private void setWindowPreferences() {
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }

    public void addIngredientRequest(IngredientRequest ingredientRequest) {
        ((DefaultListModel<IngredientRequest>) ingredientRequestListModel)
            .addElement(ingredientRequest);
    }

    public void removeIngredientRequest(int index) {
        ((DefaultListModel<IngredientRequest>) ingredientRequestListModel).remove(index);
    }

//    @EventListener
//    public void handleIngrChanged(IngredientChangedEvent event) {
//        ingredientDefaultListModel.addElement(event.getChangedIngredient());
//        System.out.println("IngredientChangedEvent: " + event.getChangedIngredient());
//    }
//
//    @EventListener
//    public void handleNewIngr(NewIngredientEvent event) {
//        ingredientDefaultListModel.addElement(event.getNewIngredient());
//        System.out.println("NewIngredientEvent: " + event.getNewIngredient());
//    }
}
