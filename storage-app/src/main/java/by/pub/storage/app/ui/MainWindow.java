package by.pub.storage.app.ui;

import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.springframework.stereotype.Component;

@Component
public class MainWindow extends JFrame {

    private final JPanel ingredientPanel;

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 30);
    private static final Object[] INGREDIENT_TABLE_HEADER = new String[]{"Name", "Amount"};
    private static final Object[] INGREDIENT_REQUEST_TABLE_HEADER = new String[]{"ID", "Name",
        "Amount", "Status"};
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;

    private final JPanel mainPanel;
    private final JPanel authPanel;
    private final JScrollPane ingredientScrollPane;
    private final JPanel ingredientRequestPanel;
    private final JScrollPane ingredientRequestScrollPane;
    private final TableModel ingredientRequestTableModel;
    private final JLabel ingredientLabel;
    private final JLabel ingredientRequestLabel;
    private final TableModel ingredientTableModel;
    private final JButton fulfillButton;
    private final JTable ingredientTable;
    private final JTable ingredientRequestTable;
    private final JButton ingredientRequestButton;

    public MainWindow() {
        authPanel = new JPanel();

        mainPanel = new JPanel(new GridLayout(1, 2));

        ingredientRequestPanel = new JPanel(new BorderLayout());
        ingredientRequestTableModel = new DefaultTableModel(INGREDIENT_REQUEST_TABLE_HEADER, 0);
        ingredientRequestTable = new IngredientRequestTable(ingredientRequestTableModel);
        ingredientRequestScrollPane = new JScrollPane(ingredientRequestTable);
        fulfillButton = new JButton("Fulfill request");
        ingredientRequestLabel = new JLabel("Requests from bartender");

        ingredientPanel = new JPanel(new BorderLayout());
        ingredientTableModel = new DefaultTableModel(INGREDIENT_TABLE_HEADER, 0);
        ingredientTable = new IngredientTable(ingredientTableModel);
        ingredientScrollPane = new JScrollPane(ingredientTable);
        ingredientRequestButton = new JButton("Request ingredients");
        ingredientLabel = new JLabel("Available ingredients");

        addListeners();
        addComponentsToIngredientPanel();
        addComponentsToIngredientRequestPanel();
        addComponentsToMainPanel();
        configureComponents();
        setWindowPreferences();

    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);

        for (int i = 0; i < 50; i++) {
            window.addIngredientRequest(
                new IngredientRequest("1", "11", "Beer", 10L, IngredientRequestStatus.ACCEPTED));
            window.addIngredientRequest(
                new IngredientRequest("2", "22", "Vodka", 1L,
                    IngredientRequestStatus.NOT_ACCEPTED));
            window.addIngredientRequest(
                new IngredientRequest("3", "33", "Jin", 15L, IngredientRequestStatus.ACCEPTED));
            window.addIngredientRequest(
                new IngredientRequest("4", "44", "Lemon", 18L, IngredientRequestStatus.ACCEPTED));

        }

        for (int i = 0; i < 50; i++) {
            window.addIngredient(
                new Ingredient("1", "Beer", 10L + (long) i));
        }
    }

    private void addListeners() {
        fulfillButton.addActionListener(e -> {
//            ListSelectionModel selectionModel = ingredientRequestJList.getSelectionModel();
//            int index = selectionModel.getMinSelectionIndex();
//            if (index >= 0) {
//                removeIngredientRequest(index);
//            }
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
        //scrollPane configuration
        ingredientScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ingredientScrollPane.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
        ingredientRequestScrollPane
            .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ingredientRequestScrollPane
            .setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
        //table configuration
        ingredientTable.setFillsViewportHeight(true);
        ingredientRequestTable.setFillsViewportHeight(true);
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
        ((DefaultTableModel) ingredientRequestTableModel).addRow(
            new Object[]{ingredientRequest.getRequestId(), ingredientRequest.getIngredientName(),
                ingredientRequest.getIngredientAmount(), ingredientRequest.getStatus()});
    }

    public void addIngredient(Ingredient ingredient) {
        ((DefaultTableModel) ingredientTableModel)
            .addRow(new Object[]{ingredient.getName(), ingredient.getAmount()});
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
