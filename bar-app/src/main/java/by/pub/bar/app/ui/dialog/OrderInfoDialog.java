package by.pub.bar.app.ui.dialog;

import by.pub.bar.app.element.ingredient.entity.Ingredient;
import by.pub.bar.app.element.order.entity.Order;
import by.pub.bar.app.element.product.entity.Product;
import by.pub.bar.app.ui.config.WindowConfig;
import by.pub.bar.app.utils.ResourceLoader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

public class OrderInfoDialog extends JDialog {

    private static final Object[] INFO_TABLE_HEADER = new String[]{"Product", "Price", "Ingredient", "Amount"};

    private final JPanel mainPanel;
    private final JPanel infoPanel;
    private final JTable infoTable;
    private final DefaultTableModel model;
    private final Order order;
    private final JScrollPane scrollPane;
    private final JButton cancelButton;
    private final JLabel orderIdLabel;
    private final JLabel priceLabel;

    public OrderInfoDialog(Order order) {
        this.order = order;
        mainPanel = new JPanel(new BorderLayout());
        infoPanel = new JPanel(new GridLayout(2, 1));
        model = new DefaultTableModel(INFO_TABLE_HEADER, 0);
        infoTable = new JTable(model);
        scrollPane = new JScrollPane(infoTable);
        cancelButton = new JButton("Cancel");
        orderIdLabel = new JLabel("Order ID: " + order.getId());
        priceLabel = new JLabel("Total price: " + order.getTotalPrice());

        addData();
        addListeners();
        addComponents();
        configureComponents();
        setWindowPreferences();
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> OrderInfoDialog.this.dispose());
    }

    private void configureComponents() {
        infoTable.setFillsViewportHeight(true);
        infoTable.setPreferredScrollableViewportSize(new Dimension(WindowConfig.getInfoDialogScreenWidth(),
                WindowConfig.getInfoDialogScreenHeight()));
        infoTable.setFont(WindowConfig.getInfoDialogTextFontFont());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(new Rectangle(WindowConfig.getInfoDialogScreenWidth(),
                WindowConfig.getInfoDialogScreenHeight()));
        orderIdLabel.setFont(WindowConfig.getInfoDialogHeaderFont());
        priceLabel.setFont(WindowConfig.getInfoDialogHeaderFont());
        cancelButton.setFont(WindowConfig.getInfoDialogHeaderFont());
    }

    private void addData() {
        for (Product product : order.getProducts()) {
            model.addRow(new Object[]{product.getName(), product.getPrice(), "", ""});
            for (Ingredient simpleIngredient : product.getUsedIngredients()) {
                model.addRow(new Object[]{"", "", simpleIngredient.getName(), simpleIngredient.getAmount()});
            }
        }
    }

    private void addComponents() {
        infoPanel.add(orderIdLabel);
        infoPanel.add(priceLabel);
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(cancelButton, BorderLayout.SOUTH);
    }

    private void setWindowPreferences() {
        setTitle("Order information");
        setIconImage(ResourceLoader.getImage("icon/order-info.png"));

        setContentPane(mainPanel);
        setResizable(false);
        pack();
    }
}
