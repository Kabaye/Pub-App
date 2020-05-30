package by.pub.bar.app.ui.dialog;

import by.pub.bar.app.order.entity.Order;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OrderInfoDialog extends JDialog {

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 15);
    private static final int SCREEN_WIDTH = 250;
    private static final int SCREEN_HEIGHT = 100;

    private final JPanel mainPanel;
    private final JPanel infoPanel;
    //    private final JTable infoTable;
//    private final DefaultTableModel model;
    private final Order order;


    public OrderInfoDialog(Order order) {
        this.order = order;
        mainPanel = new JPanel(new BorderLayout());
        infoPanel = new JPanel(new GridLayout(2, 1));

        addComponents();
        setWindowPreferences();
    }

    private void addComponents() {
        infoPanel.add(new JLabel("Order ID: " + order.getId()));
        infoPanel.add(new JLabel("Total price: " + order.getTotalPrice()));
        mainPanel.add(infoPanel, BorderLayout.CENTER);
    }

    private void setWindowPreferences() {
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setResizable(false);
        pack();
    }
}
