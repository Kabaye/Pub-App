package by.pub.storage.app.ui;

import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class IngredientRequestTable extends JTable {

    public IngredientRequestTable(TableModel dm) {
        super(dm);
    }

    @Override
    public Class getColumnClass(int column) {
        //return getValueAt(0,column).getClass();
        switch (column) {
            case 0:
            case 2:
                return Long.class;
            case 3:
                return IngredientRequestStatus.class;
            case 1:
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
