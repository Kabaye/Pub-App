package by.pub.bar.app.ui.frame;

import by.pub.bar.app.ingredient.service.IngredientService;
import by.pub.bar.app.order.entity.Order;
import by.pub.bar.app.order.service.OrderService;
import by.pub.bar.app.security.service.CredentialsService;
import by.pub.bar.app.ui.dialog.RequestStoreKeeperDialog;
import by.pub.bar.app.ui.table_model.IngredientTableModel;
import by.pub.bar.app.ui.table_model.OrderTableModel;
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
import org.springframework.stereotype.Component;

@Component
public class MainWindow extends JFrame {

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 30);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 20);
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;

    private final JPanel mainPanel;
    private final JPanel authPanel;
    private final JScrollPane ingredientScrollPane;
    private final JScrollPane orderScrollPane;
    private final JPanel ingredientPanel;
    private final JPanel orderPanel;
    private final JLabel ingredientLabel;
    private final JLabel orderLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JTable ingredientTable;
    private final JTable orderTable;
    private final IngredientTableModel ingredientTableModel;
    private final OrderTableModel orderTableModel;
    private final JButton ingredientRequestButton;
    private final JButton fulfillButton;
    private final JButton logInButton;
    private final JButton exitButton;
    private final JTextField usernameTextField;
    private final JPasswordField passwordTextField;
    private final JCheckBox passwordCheckBox;

    private final IngredientService ingredientService;
    private final OrderService orderService;
    private final CredentialsService credentialsService;
    private final RequestStoreKeeperDialog requestStoreKeeperDialog;

    private final JMenuBar menuBar;
    private final JMenu userMenu;
    private final JMenu orderMenu;
    private final JMenuItem signOutItem;
    private final JMenuItem clearOrderItem;

    public MainWindow(
        IngredientTableModel ingredientTableModel,
        OrderTableModel orderTableModel,
        IngredientService ingredientService,
        OrderService orderService,
        CredentialsService credentialsService,
        RequestStoreKeeperDialog requestStoreKeeperDialog) {
        this.ingredientTableModel = ingredientTableModel;
        this.orderTableModel = orderTableModel;
        this.ingredientService = ingredientService;
        this.orderService = orderService;
        this.credentialsService = credentialsService;
        this.requestStoreKeeperDialog = requestStoreKeeperDialog;
        //authPanel
        authPanel = new JPanel(new GridLayout(4, 2, 1, 0));
        usernameLabel = new JLabel("Username", JLabel.CENTER);
        passwordLabel = new JLabel("Password", JLabel.CENTER);
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        logInButton = new JButton("Log in");
        exitButton = new JButton("Exit");
        passwordCheckBox = new JCheckBox("Show pass", false);
        addComponentsToAuthPanel();
        configureAuthPanelComponents();
        //mainPanel
        mainPanel = new JPanel(new GridLayout(1, 2));
        orderPanel = new JPanel(new BorderLayout());

        orderTable = new JTable(this.orderTableModel);//new OrderTable(this.orderTableModel);
        orderScrollPane = new JScrollPane(orderTable);
        fulfillButton = new JButton("Fulfill order");
        orderLabel = new JLabel("Requests from clients");

        ingredientPanel = new JPanel(new BorderLayout());
        ingredientTable = new JTable(
            this.ingredientTableModel);//new IngredientTable(this.ingredientTableModel);
        ingredientScrollPane = new JScrollPane(ingredientTable);
        ingredientRequestButton = new JButton("Request ingredients");
        ingredientLabel = new JLabel("Available ingredients");
        //menu
        signOutItem = new JMenuItem("Sign out");
        clearOrderItem = new JMenuItem("Clear accepted orders");
        menuBar = new JMenuBar();
        userMenu = new JMenu("User");
        orderMenu = new JMenu("Orders");

        loadDataToModels();
        addListeners();
        addMenuListeners();
        addComponentsToIngredientPanel();
        addComponentsToOrderPanel();
        addComponentsToMainPanel();
        configureComponents();
        configureMenu();
        setWindowPreferences();
    }

    private void addMenuListeners() {
        signOutItem.addActionListener(e -> showAuthPanel());
        clearOrderItem.addActionListener(e -> {
//            if (orderTableModel.removeAcceptedRows() == 0) {
//                JOptionPane
//                    .showMessageDialog(MainWindow.this,
//                        "Nothing to clear", "Warning", JOptionPane.WARNING_MESSAGE);
//            }
        });
    }

    private void configureMenu() {
        userMenu.add(signOutItem);
        orderMenu.add(clearOrderItem);
        menuBar.add(userMenu);
        menuBar.add(orderMenu);
        setJMenuBar(menuBar);

        menuBar.setVisible(false);
    }

    private void loadDataToModels() {
//        for (Order order : orderService.findAllOrders()) {
//            orderTableModel.addRow(order);
//        }
//        for (Ingredient ingredient : ingredientService.findAllIngredients()) {
//            ingredientTableModel.addRow(ingredient);
//        }
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
                JOptionPane
                    .showMessageDialog(MainWindow.this,
                        "Invalid login and password", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        exitButton.addActionListener(e -> MainWindow.this
            .dispatchEvent(new WindowEvent(MainWindow.this, WindowEvent.WINDOW_CLOSING)));
        fulfillButton.addActionListener(e -> {
            ListSelectionModel selectionModel = orderTable.getSelectionModel();
            int index = selectionModel.getMinSelectionIndex();
            Order order;
            if (index >= 0) {
//                ingredientRequest = orderTableModel.getValueAt(index);
//                if (ingredientRequest.getStatus().equals(IngredientRequestStatus.NOT_ACCEPTED)) {
//                    try {
//                        ingredientRequest = ingredientRequestService
//                            .acceptIngredientRequest(ingredientRequest);
//                        ingredientRequestService
//                            .deleteByRequestId(ingredientRequest.getRequestId());
//                        orderTableModel.removeRow(index);
//                        orderTableModel.addRow(ingredientRequest);
//                    } catch (RuntimeException exception) {
//                        JOptionPane
//                            .showMessageDialog(MainWindow.this,
//                                exception.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
//                    }
//                } else {
//                    JOptionPane
//                        .showMessageDialog(MainWindow.this,
//                            "Request is already accepted", "Warning", JOptionPane.WARNING_MESSAGE);
//                }
            }
        });
        ingredientRequestButton.addActionListener(e -> requestStoreKeeperDialog.setVisible(true));
    }

    private void addComponentsToIngredientPanel() {
        ingredientPanel.add(ingredientLabel, BorderLayout.NORTH);
        ingredientPanel.add(ingredientScrollPane, BorderLayout.CENTER);
        ingredientPanel.add(ingredientRequestButton, BorderLayout.SOUTH);
    }

    private void addComponentsToOrderPanel() {
        orderPanel.add(orderLabel, BorderLayout.NORTH);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);
        orderPanel.add(fulfillButton, BorderLayout.SOUTH);
    }

    private void addComponentsToMainPanel() {
        mainPanel.add(orderPanel);
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
        orderLabel.setFont(HEADER_FONT);
        orderLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void configureTables() {
//        ingredientTable.setFillsViewportHeight(true);
//        for (int i = 0; i < ingredientTable.getColumnCount(); i++) {
//            ingredientTable.getColumnModel().getColumn(i)
//                .setCellRenderer(new IngredientTextRenderer());
//        }
//        orderTable.setFillsViewportHeight(true);
//        for (int i = 0; i < orderTable.getColumnCount() - 1; i++) {
//            orderTable.getColumnModel().getColumn(i)
//                .setCellRenderer(new OrderTextRenderer());
//        }
//        orderTable.getColumnModel()
//            .getColumn(orderTable.getColumnCount() - 1)
//            .setCellRenderer(new OrderStatusRenderer());
//        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configureScrollPanes() {
        ingredientScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ingredientScrollPane.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
        orderScrollPane
            .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderScrollPane
            .setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
    }

    private void configurePanels() {
        orderPanel.setBorder(BorderFactory.createBevelBorder(1));
        ingredientPanel.setBorder(BorderFactory.createBevelBorder(1));
    }

    private void setWindowPreferences() {
        showAuthPanel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
//        pack();
    }

    private void showAuthPanel() {
        JComponent contentPane = (JPanel) MainWindow.this.getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(authPanel, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();

        menuBar.setVisible(false);
        setBounds(0, 0, SCREEN_WIDTH / 4, SCREEN_HEIGHT / 5);
    }

    private void showMainPanel() {
        JComponent contentPane = (JPanel) MainWindow.this.getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();

        menuBar.setVisible(true);
        MainWindow.this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

//    @EventListener
//    public void handleIngredientRequesting(NewIngredientRequestEvent event) {
//        orderTableModel.addRow(event.getIngredientRequest());
//    }
//
//    @EventListener
//    public void handleIngredientChanging(IngredientChangedEvent event) {
//        ingredientTableModel.removeRow(event.getIngredient());
//        ingredientTableModel.addRow(event.getIngredient());
//    }
}