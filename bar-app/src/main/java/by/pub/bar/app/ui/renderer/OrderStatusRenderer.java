package by.pub.bar.app.ui.renderer;

import by.pub.bar.app.utils.Status;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class OrderStatusRenderer extends DefaultTableCellRenderer {

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 15);

    @Override
    public Component getTableCellRendererComponent(JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(
            table,
            value,
            isSelected,
            hasFocus,
            row,
            column);
        label.setFont(HEADER_FONT);
        Status status = (Status) value;
        if (status.equals(Status.ACCEPTED)) {
            label.setText("Accepted");
            label.setForeground(Color.GREEN);
        } else if (status.equals(Status.NOT_ACCEPTED)) {
            label.setText("Not accepted");
            label.setForeground(Color.RED);
        }
        return label;
    }
}
