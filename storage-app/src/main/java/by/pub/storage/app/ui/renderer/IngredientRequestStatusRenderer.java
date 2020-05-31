package by.pub.storage.app.ui.renderer;

import by.pub.storage.app.element.ingredient_request.entity.IngredientRequestStatus;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class IngredientRequestStatusRenderer extends DefaultTableCellRenderer {

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
        IngredientRequestStatus status = (IngredientRequestStatus) value;
        if (status.equals(IngredientRequestStatus.ACCEPTED)) {
            label.setText("Accepted");
            label.setForeground(Color.GREEN);
        } else if (status.equals(IngredientRequestStatus.NOT_ACCEPTED)) {
            label.setText("Not accepted");
            label.setForeground(Color.RED);
        }
        return label;
    }

}
