package by.pub.client.app.ui.frame;

import by.pub.client.app.event.entity.ReceivedAcceptedOrderEvent;
import by.pub.client.app.order.entity.Order;
import by.pub.client.app.product.entity.Product;
import by.pub.client.app.service.ClientService;
import by.pub.client.app.ui.config.WindowConfig;
import by.pub.client.app.ui.dialog.OrderInfoDialog;
import by.pub.client.app.ui.renderer.OrderStatusRenderer;
import by.pub.client.app.ui.renderer.OrderTextRenderer;
import by.pub.client.app.ui.table.OrderTable;
import by.pub.client.app.ui.table.ProductTable;
import by.pub.client.app.ui.table_model.OrderTableModel;
import by.pub.client.app.ui.table_model.ProductTableModel;
import by.pub.client.app.utils.ResourceLoader;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

@Component
public class MainWindow extends JFrame {

    private final JPanel mainPanel;
    private final JPanel authPanel;
    private final JScrollPane productScrollPane;
    private final JScrollPane orderScrollPane;
    private final JPanel productPanel;
    private final JPanel orderPanel;
    private final JLabel productLabel;
    private final JLabel orderLabel;
    private final JLabel usernameLabel;
    private final JButton usernameButton;
    private final JTable productTable;
    private final JTable orderTable;
    private final ProductTableModel productTableModel;
    private final OrderTableModel orderTableModel;
    private final JButton orderButton;
    private final JButton enterButton;
    private final JButton exitButton;
    private final JTextField usernameTextField;

    private final ClientService clientService;

    private final JMenuBar menuBar;
    private final JMenu userMenu;
    private final JMenu orderMenu;
    private final JMenuItem leaveItem;
    private final JMenuItem clearOrderItem;

    private String clientId;
    private String clientName;

    private final String appVersion;

    public MainWindow(ProductTableModel productTableModel, OrderTableModel orderTableModel, ClientService clientService,
                      String appVersion) {
        this.productTableModel = productTableModel;
        this.orderTableModel = orderTableModel;
        this.clientService = clientService;
        this.appVersion = appVersion;
        //authPanel
        authPanel = new JPanel(new GridLayout(2, 2, 1, 0));
        enterButton = new JButton("Enter bar");
        exitButton = new JButton("Exit");
        usernameLabel = new JLabel("Username");
        usernameTextField = new JTextField();
        addComponentsToAuthPanel();
        configureAuthPanelComponents();
        //mainPanel
        mainPanel = new JPanel(new GridLayout(1, 2));

        productPanel = new JPanel(new BorderLayout());
        productTable = new ProductTable(this.productTableModel);
        productScrollPane = new JScrollPane(productTable);
        orderButton = new JButton("Order");
        productLabel = new JLabel("Available products");

        orderPanel = new JPanel(new BorderLayout());
        orderTable = new OrderTable(this.orderTableModel);
        orderScrollPane = new JScrollPane(orderTable);
        orderLabel = new JLabel("Your orders");
        usernameButton = new JButton();
        //menu
        leaveItem = new JMenuItem("Sign out");
        clearOrderItem = new JMenuItem("Clear accepted requests");
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
        orderMenu = new JMenu("Orders");

        loadDataToModels();
        addListeners();
        addMenuListeners();
        addComponentsToProductPanel();
        addComponentsToOrderPanel();
        addComponentsToMainPanel();
        configureComponents();
        configureMenu();
        setWindowPreferences();
    }

    private void addMenuListeners() {
        leaveItem.addActionListener(e -> showAuthPanel());
        clearOrderItem.addActionListener(e -> {
            if (orderTableModel.removeAcceptedRows() == 0) {
                JOptionPane
                        .showMessageDialog(MainWindow.this,
                                "Nothing to clear", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void configureMenu() {
        userMenu.add(leaveItem);
        orderMenu.add(clearOrderItem);
        menuBar.add(userMenu);
        menuBar.add(orderMenu);
        setJMenuBar(menuBar);

        menuBar.setVisible(false);
    }

    private void loadDataToModels() {
        for (Product product : clientService.findAllProducts()) {
            productTableModel.addRow(product);
        }
    }

    private void addComponentsToAuthPanel() {
        authPanel.add(usernameLabel);
        authPanel.add(usernameTextField);
        authPanel.add(exitButton);
        authPanel.add(enterButton);
    }

    private void configureAuthPanelComponents() {
        usernameLabel.setFont(WindowConfig.getTextFont());
        enterButton.setFont(WindowConfig.getTextFont());
        exitButton.setFont(WindowConfig.getTextFont());
    }

    private void addListeners() {
        enterButton.addActionListener(e -> {
            if (usernameTextField.getText().compareTo("") != 0) {
                showMainPanel();
            } else {
                JOptionPane.showMessageDialog(MainWindow.this,
                    "Please, type your name in", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        });
        exitButton.addActionListener(e -> MainWindow.this
            .dispatchEvent(new WindowEvent(MainWindow.this, WindowEvent.WINDOW_CLOSING)));
        orderButton.addActionListener(e -> {
            ListSelectionModel selectionModel = productTable.getSelectionModel();
            int[] selectedRows = selectionModel.getSelectedIndices();
            Order order;
            if (selectedRows.length > 0) {
                order = clientService
                        .requestOrder(clientId, productTableModel.getValues(selectedRows));
                orderTableModel.addRow(order);
            } else {
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Please, select products", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        orderTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    OrderInfoDialog infoDialog = new OrderInfoDialog(
                            orderTableModel.getValueAt(row));
                    infoDialog.setVisible(true);
                }
            }
        });
    }

    private void addComponentsToProductPanel() {
        productPanel.add(productLabel, BorderLayout.NORTH);
        productPanel.add(productScrollPane, BorderLayout.CENTER);
        productPanel.add(orderButton, BorderLayout.SOUTH);
    }

    private void addComponentsToOrderPanel() {
        orderPanel.add(orderLabel, BorderLayout.NORTH);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);
        orderPanel.add(usernameButton, BorderLayout.SOUTH);
    }

    private void addComponentsToMainPanel() {
        mainPanel.add(productPanel);
        mainPanel.add(orderPanel);
    }

    private void configureComponents() {
        configurePanels();
        configureScrollPanes();
        configureTables();
        configureLabels();
        configureButtons();
    }

    private void configureButtons() {
        orderButton.setFont(WindowConfig.getHeaderFont());
        usernameButton.setFont(WindowConfig.getHeaderFont());
        usernameButton.setEnabled(false);
    }

    private void configureLabels() {
        orderLabel.setFont(WindowConfig.getHeaderFont());
        orderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productLabel.setFont(WindowConfig.getHeaderFont());
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void configureTables() {
        productTable.setFillsViewportHeight(true);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i)
                    .setCellRenderer(new OrderTextRenderer());
        }
        orderTable.setFillsViewportHeight(true);
        for (int i = 0; i < orderTable.getColumnCount() - 1; i++) {
            orderTable.getColumnModel().getColumn(i)
                    .setCellRenderer(new OrderTextRenderer());
        }
        orderTable.getColumnModel()
                .getColumn(orderTable.getColumnCount() - 1)
                .setCellRenderer(new OrderStatusRenderer());
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configureScrollPanes() {
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        productScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void configurePanels() {
        productPanel.setBorder(BorderFactory.createBevelBorder(1));
        orderPanel.setBorder(BorderFactory.createBevelBorder(1));
    }

    private void setWindowPreferences() {
        showAuthPanel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void showAuthPanel() {
        setTitle("Who are you?");
        setIconImage(ResourceLoader.getImage("icon/who-are-you.png"));

        clientId = null;
        clientName = null;
        orderTableModel.removeAllRows();
        usernameButton.setText("");

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
        setTitle("Client app " + appVersion);
        setIconImage(ResourceLoader.getImage("icon/client.png"));

        clientId = clientService.createUniqueID();
        clientName = usernameTextField.getText();
        usernameButton.setText("Client: " + clientName);

        JComponent contentPane = (JPanel) MainWindow.this.getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();

        menuBar.setVisible(true);
        MainWindow.this
                .setBounds(0, 0, WindowConfig.getScreenWidth(), WindowConfig.getScreenHeight());
    }

    @EventListener
    public void handleAcceptedOrderReceiving(
        ReceivedAcceptedOrderEvent receivedAcceptedOrderEvent) {
        if (receivedAcceptedOrderEvent.getOrder().getClientId().compareTo(clientId) == 0) {
            orderTableModel.removeRow(receivedAcceptedOrderEvent.getOrder());
            orderTableModel.addRow(receivedAcceptedOrderEvent.getOrder());
        }
    }

}
