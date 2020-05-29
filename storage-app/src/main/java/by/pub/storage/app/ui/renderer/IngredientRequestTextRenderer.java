package by.pub.storage.app.ui.renderer;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class IngredientRequestTextRenderer extends DefaultTableCellRenderer {

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
        return label;
    }
}
