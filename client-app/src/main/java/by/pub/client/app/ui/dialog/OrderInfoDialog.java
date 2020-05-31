package by.pub.client.app.ui.dialog;

import by.pub.client.app.order.entity.Order;
import by.pub.client.app.product.entity.Product;
import by.pub.client.app.ui.utils.WindowUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OrderInfoDialog extends JDialog {

    private static final Object[] INFO_TABLE_HEADER = new String[]{"Product", "Price"};

    private final JPanel mainPanel;
    private final JPanel infoPanel;
    private final JTable infoTable;
    private final DefaultTableModel model;
    private final Order order;
    private final JScrollPane scrollPane;
    private final JButton cancelButton;
    private final JLabel priceLabel;

    public OrderInfoDialog(Order order) {
        this.order = order;
        mainPanel = new JPanel(new BorderLayout());
        infoPanel = new JPanel(new GridLayout(1, 1));
        model = new DefaultTableModel(INFO_TABLE_HEADER, 0);
        infoTable = new JTable(model);
        scrollPane = new JScrollPane(infoTable);
        cancelButton = new JButton("Cancel");
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
        infoTable.setPreferredScrollableViewportSize(
            new Dimension(WindowUtils.getInfoDialogScreenWidth(),
                WindowUtils.getInfoDialogScreenHeight()));
        infoTable.setFont(WindowUtils.getInfoDialogTextFontFont());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(new Rectangle(WindowUtils.getInfoDialogScreenWidth(),
            WindowUtils.getInfoDialogScreenHeight()));
        priceLabel.setFont(WindowUtils.getInfoDialogHeaderFont());
        cancelButton.setFont(WindowUtils.getInfoDialogHeaderFont());
    }

    private void addData() {
        for (Product product : order.getProducts()) {
            model.addRow(new Object[]{product.getName(), product.getPrice()});
        }
    }

    private void addComponents() {
        infoPanel.add(priceLabel);
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(cancelButton, BorderLayout.SOUTH);
    }

    private void setWindowPreferences() {
        setTitle("Order info");
        setContentPane(mainPanel);
//        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setResizable(false);
        pack();
    }

}