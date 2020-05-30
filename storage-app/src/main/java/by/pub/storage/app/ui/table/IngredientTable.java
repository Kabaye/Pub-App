package by.pub.storage.app.ui.table;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class IngredientTable extends JTable {

    public IngredientTable(TableModel dm) {
        super(dm);
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 1:
                return Long.class;
            case 0:
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
