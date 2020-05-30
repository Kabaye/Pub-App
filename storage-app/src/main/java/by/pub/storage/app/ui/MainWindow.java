package by.pub.storage.app.ui;

import by.pub.storage.app.event.entity.IngredientChangedEvent;
import by.pub.storage.app.event.entity.NewIngredientRequestEvent;
import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient.service.IngredientService;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import by.pub.storage.app.ingredient_request.service.IngredientRequestService;
import by.pub.storage.app.security.service.CredentialsService;
import by.pub.storage.app.ui.dialog.RequestProviderDialog;
import by.pub.storage.app.ui.renderer.IngredientRequestStatusRenderer;
import by.pub.storage.app.ui.renderer.IngredientRequestTextRenderer;
import by.pub.storage.app.ui.table.IngredientRequestTable;
import by.pub.storage.app.ui.table.IngredientTable;
import by.pub.storage.app.ui.table_model.IngredientRequestTableModel;
import by.pub.storage.app.ui.table_model.IngredientTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MainWindow extends JFrame {

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 30);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 25);
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;

    private final JPanel mainPanel;
    private final JPanel authPanel;
    private final JScrollPane ingredientScrollPane;
    private final JScrollPane ingredientRequestScrollPane;
    private final JPanel ingredientPanel;
    private final JPanel ingredientRequestPanel;
    private final JLabel ingredientLabel;
    private final JLabel ingredientRequestLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JTable ingredientTable;
    private final JTable ingredientRequestTable;
    private final IngredientTableModel ingredientTableModel;
    private final IngredientRequestTableModel ingredientRequestTableModel;
    private final JButton ingredientRequestButton;
    private final JButton fulfillButton;
    private final JButton logInButton;
    private final JButton exitButton;
    private final JTextField usernameTextField;
    private final JPasswordField passwordTextField;
    private final JCheckBox passwordCheckBox;

    private final IngredientService ingredientService;
    private final IngredientRequestService ingredientRequestService;
    private final CredentialsService credentialsService;
    private final RequestProviderDialog requestProviderDialog;

    public MainWindow(
        IngredientTableModel ingredientTableModel,
        IngredientRequestTableModel ingredientRequestTableModel,
        IngredientService ingredientService,
        IngredientRequestService ingredientRequestService,
        CredentialsService credentialsService,
        RequestProviderDialog requestProviderDialog) {
        this.ingredientTableModel = ingredientTableModel;
        this.ingredientRequestTableModel = ingredientRequestTableModel;
        this.ingredientService = ingredientService;
        this.ingredientRequestService = ingredientRequestService;
        this.credentialsService = credentialsService;
        this.requestProviderDialog = requestProviderDialog;
        //authPanel
        authPanel = new JPanel(new GridLayout(4, 2, 1, 0));
        usernameLabel = new JLabel("Username", JLabel.CENTER);
        passwordLabel = new JLabel("Password", JLabel.CENTER);
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        logInButton = new JButton("Log in");
        exitButton = new JButton("Exit");
        passwordCheckBox = new JCheckBox("Show password", false);
        addComponentsToAuthPanel();
        configureAuthPanelComponents();
        //mainPanel
        mainPanel = new JPanel(new GridLayout(1, 2));
        ingredientRequestPanel = new JPanel(new BorderLayout());

        ingredientRequestTable = new IngredientRequestTable(this.ingredientRequestTableModel);
        ingredientRequestScrollPane = new JScrollPane(ingredientRequestTable);
        fulfillButton = new JButton("Fulfill request");
        ingredientRequestLabel = new JLabel("Requests from bartender");

        ingredientPanel = new JPanel(new BorderLayout());
        ingredientTable = new IngredientTable(this.ingredientTableModel);
        ingredientScrollPane = new JScrollPane(ingredientTable);
        ingredientRequestButton = new JButton("Request ingredients");
        ingredientLabel = new JLabel("Available ingredients");

        loadDataToModels();
        addListeners();
        addComponentsToIngredientPanel();
        addComponentsToIngredientRequestPanel();
        addComponentsToMainPanel();
        configureComponents();
        setWindowPreferences();
    }

    private void loadDataToModels() {
        for (IngredientRequest ingredientRequest : ingredientRequestService
            .findAllIngredientRequests()) {
            ingredientRequestTableModel.addRow(ingredientRequest);
        }
        for (Ingredient ingredient : ingredientService.findAllIngredients()) {
            ingredientTableModel.addRow(ingredient);
        }
    }

    private void addComponentsToAuthPanel() {
        authPanel.add(usernameLabel);
        authPanel.add(passwordLabel);
        authPanel.add(usernameTextField);
        authPanel.add(passwordTextField);
        authPanel.add(new JPanel());
        authPanel.add(passwordCheckBox);
        authPanel.add(exitButton);
        authPanel.add(logInButton);
    }

    private void configureAuthPanelComponents() {
        usernameLabel.setFont(TEXT_FONT);
        passwordLabel.setFont(TEXT_FONT);
        logInButton.setFont(TEXT_FONT);
        exitButton.setFont(TEXT_FONT);
        passwordTextField.setEchoChar('*');
    }

    private void addListeners() {
        // TODO: 5/30/20 add sign out ability
        passwordCheckBox.addActionListener(e -> {
            if (passwordCheckBox.isSelected()) {
                passwordTextField.setEchoChar((char) 0);
            } else {
                passwordTextField.setEchoChar('*');
            }
        });
        logInButton.addActionListener(e -> {
            if (credentialsService
                .checkCredentials(usernameTextField.getText(),
                    new String(passwordTextField.getPassword()))) {
                JComponent contentPane = (JPanel) MainWindow.this.getContentPane();
                contentPane.removeAll();
                contentPane.setLayout(new BorderLayout());
                contentPane.add(mainPanel, BorderLayout.CENTER);
                contentPane.revalidate();
                contentPane.repaint();
                MainWindow.this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            } else {
                JOptionPane
                    .showMessageDialog(MainWindow.this,
                        "Invalid login and password");
            }
        });
        exitButton.addActionListener(e -> MainWindow.this
            .dispatchEvent(new WindowEvent(MainWindow.this, WindowEvent.WINDOW_CLOSING)));
        fulfillButton.addActionListener(e -> {
            ListSelectionModel selectionModel = ingredientRequestTable.getSelectionModel();
            int index = selectionModel.getMinSelectionIndex();
            IngredientRequest ingredientRequest;
            if (index >= 0) {
                ingredientRequest = ingredientRequestTableModel.getValueAt(index);
                if (ingredientRequest.getStatus().equals(IngredientRequestStatus.NOT_ACCEPTED)) {
                    try {
                        ingredientRequest = ingredientRequestService
                            .acceptIngredientRequest(ingredientRequest);
                        ingredientRequestService
                            .deleteByRequestId(ingredientRequest.getRequestId());
                        ingredientRequestTableModel.removeRow(index);
                        ingredientRequestTableModel.addRow(ingredientRequest);
                    } catch (RuntimeException exception) {
                        JOptionPane
                            .showMessageDialog(MainWindow.this,
                                exception.getMessage());
                    }
                } else {
                    JOptionPane
                        .showMessageDialog(MainWindow.this,
                            "Request is already accepted");
                }
            }
        });
        ingredientRequestButton.addActionListener(e -> requestProviderDialog.setVisible(true));
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
        configurePanels();
        configureScrollPanes();
        configureTables();
        configureLabels();
        configureButtons();
    }

    private void configureButtons() {
        fulfillButton.setFont(HEADER_FONT);
        ingredientRequestButton.setFont(HEADER_FONT);
    }

    private void configureLabels() {
        ingredientLabel.setFont(HEADER_FONT);
        ingredientLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingredientRequestLabel.setFont(HEADER_FONT);
        ingredientRequestLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void configureTables() {
        ingredientTable.setFillsViewportHeight(true);
        for (int i = 0; i < ingredientTable.getColumnCount(); i++) {
            ingredientTable.getColumnModel().getColumn(i)
                .setCellRenderer(new IngredientRequestTextRenderer());
        }
        ingredientRequestTable.setFillsViewportHeight(true);
        for (int i = 0; i < ingredientRequestTable.getColumnCount() - 1; i++) {
            ingredientRequestTable.getColumnModel().getColumn(i)
                .setCellRenderer(new IngredientRequestTextRenderer());
        }
        ingredientRequestTable.getColumnModel()
            .getColumn(ingredientRequestTable.getColumnCount() - 1)
            .setCellRenderer(new IngredientRequestStatusRenderer());
        ingredientRequestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configureScrollPanes() {
        ingredientScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ingredientScrollPane.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
        ingredientRequestScrollPane
            .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ingredientRequestScrollPane
            .setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
    }

    private void configurePanels() {
        ingredientRequestPanel.setBorder(BorderFactory.createBevelBorder(1));
        ingredientPanel.setBorder(BorderFactory.createBevelBorder(1));
    }

    private void setWindowPreferences() {
        setContentPane(authPanel);
        setBounds(0, 0, SCREEN_WIDTH / 10, SCREEN_HEIGHT / 20);
//        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }

    @EventListener
    public void handleIngredientRequesting(NewIngredientRequestEvent event) {
        ingredientRequestTableModel.addRow(event.getIngredientRequest());
    }

    @EventListener
    public void handleIngredientChanging(IngredientChangedEvent event) {
        ingredientTableModel.removeRow(event.getIngredient());
        ingredientTableModel.addRow(event.getIngredient());
    }
}
