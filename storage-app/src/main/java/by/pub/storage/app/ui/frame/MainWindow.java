package by.pub.storage.app.ui.frame;

import by.pub.storage.app.element.ingredient.entity.Ingredient;
import by.pub.storage.app.element.ingredient.service.IngredientService;
import by.pub.storage.app.element.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.element.ingredient_request.entity.IngredientRequestStatus;
import by.pub.storage.app.element.ingredient_request.service.IngredientRequestService;
import by.pub.storage.app.event.entity.IngredientChangedEvent;
import by.pub.storage.app.event.entity.NewIngredientRequestEvent;
import by.pub.storage.app.security.service.CredentialsService;
import by.pub.storage.app.ui.config.WindowConfig;
import by.pub.storage.app.ui.dialog.RequestProviderDialog;
import by.pub.storage.app.ui.renderer.IngredientRequestStatusRenderer;
import by.pub.storage.app.ui.renderer.IngredientRequestTextRenderer;
import by.pub.storage.app.ui.table.IngredientRequestTable;
import by.pub.storage.app.ui.table.IngredientTable;
import by.pub.storage.app.ui.table_model.IngredientRequestTableModel;
import by.pub.storage.app.ui.table_model.IngredientTableModel;
import by.pub.storage.app.utils.ResourceLoader;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

@Component
public class MainWindow extends JFrame {

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
    private final String appVersion;

    private final JMenuBar menuBar;
    private final JMenu userMenu;
    private final JMenu requestMenu;
    private final JMenuItem signOutItem;
    private final JMenuItem clearRequestItem;

    public MainWindow(IngredientTableModel ingredientTableModel, IngredientRequestTableModel ingredientRequestTableModel,
                      IngredientService ingredientService, IngredientRequestService ingredientRequestService,
                      CredentialsService credentialsService, RequestProviderDialog requestProviderDialog, String appVersion) {
        this.ingredientTableModel = ingredientTableModel;
        this.ingredientRequestTableModel = ingredientRequestTableModel;
        this.ingredientService = ingredientService;
        this.ingredientRequestService = ingredientRequestService;
        this.credentialsService = credentialsService;
        this.requestProviderDialog = requestProviderDialog;
        this.appVersion = appVersion;
        //authPanel
        authPanel = new JPanel(new GridLayout(4, 2, 1, 0));
        usernameLabel = new JLabel("Username", JLabel.CENTER);
        passwordLabel = new JLabel("Password", JLabel.CENTER);
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        logInButton = new JButton("Log in");
        exitButton = new JButton("Exit");
        passwordCheckBox = new JCheckBox("Show pass");
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
        //menu
        signOutItem = new JMenuItem("Sign out");
        clearRequestItem = new JMenuItem("Clear accepted requests");
        menuBar = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(WindowConfig.getMenuBarColor());
                g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        userMenu = new JMenu("User");
        requestMenu = new JMenu("Ingredient Requests");

        loadDataToModels();
        addListeners();
        addMenuListeners();
        addComponentsToIngredientPanel();
        addComponentsToIngredientRequestPanel();
        addComponentsToMainPanel();
        configureComponents();
        configureMenu();
        setWindowPreferences();
    }

    private void addMenuListeners() {
        signOutItem.addActionListener(e -> showAuthPanel());
        clearRequestItem.addActionListener(e -> {
            if (ingredientRequestTableModel.removeAcceptedRows() == 0) {
                JOptionPane
                        .showMessageDialog(MainWindow.this,
                                "Nothing to clear", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void configureMenu() {
        userMenu.add(signOutItem);
        requestMenu.add(clearRequestItem);
        menuBar.add(userMenu);
        menuBar.add(requestMenu);
        setJMenuBar(menuBar);

        menuBar.setVisible(false);
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
        usernameLabel.setFont(WindowConfig.getTextFont());
        passwordLabel.setFont(WindowConfig.getTextFont());
        logInButton.setFont(WindowConfig.getTextFont());
        exitButton.setFont(WindowConfig.getTextFont());
        passwordTextField.setEchoChar('*');
    }

    private void addListeners() {
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
                showMainPanel();
            } else {
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Invalid login and password", "Warning", JOptionPane.WARNING_MESSAGE);
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
                        ingredientRequestTableModel.removeRow(index);
                        ingredientRequestTableModel.addRow(ingredientRequest);
                    } catch (RuntimeException exception) {
                        JOptionPane.showMessageDialog(MainWindow.this,
                                exception.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Request is already accepted", "Warning", JOptionPane.WARNING_MESSAGE);
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
        fulfillButton.setFont(WindowConfig.getHeaderFont());
        ingredientRequestButton.setFont(WindowConfig.getHeaderFont());
    }

    private void configureLabels() {
        ingredientLabel.setFont(WindowConfig.getHeaderFont());
        ingredientLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingredientRequestLabel.setFont(WindowConfig.getHeaderFont());
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
        ingredientRequestScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void configurePanels() {
        ingredientRequestPanel.setBorder(BorderFactory.createBevelBorder(1));
        ingredientPanel.setBorder(BorderFactory.createBevelBorder(1));
    }

    private void setWindowPreferences() {
        showAuthPanel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
//        pack();
    }

    private void showAuthPanel() {
        setTitle("Storage log in");
        setIconImage(ResourceLoader.getImage("icon/authentication.png"));

        passwordCheckBox.setSelected(false);
        passwordTextField.setText("");
        usernameTextField.setText("");

        JComponent contentPane = (JPanel) MainWindow.this.getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(authPanel, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();

        menuBar.setVisible(false);
        setBounds(0, 0, WindowConfig.getAuthScreenWidth(), WindowConfig.getAuthScreenHeight());
    }

    private void showMainPanel() {
        setTitle("Storage app " + appVersion);
        setIconImage(ResourceLoader.getImage("icon/storage.png"));

        JComponent contentPane = (JPanel) getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();

        menuBar.setVisible(true);
        setBounds(0, 0, WindowConfig.getScreenWidth(), WindowConfig.getScreenHeight());
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
