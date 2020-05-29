package by.pub.storage.app.ui.table_model;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class IngredientRequestTableModel extends DefaultTableModel {

    List<IngredientRequest> ingredientRequests;

    public IngredientRequestTableModel(List<IngredientRequest> list, Object[] columnNames,
        int rowCount) {
        super(columnNames, rowCount);
        ingredientRequests = list;
        for (IngredientRequest ingredientRequest : list) {
            addRow(new Object[]{ingredientRequest.getRequestId(),
                ingredientRequest.getIngredientName(),
                ingredientRequest.getIngredientAmount(), ingredientRequest.getStatus()});
        }
    }

    public void addRow(IngredientRequest ingredientRequest) {
        super.addRow(
            new Object[]{ingredientRequest.getRequestId(), ingredientRequest.getIngredientName(),
                ingredientRequest.getIngredientAmount(), ingredientRequest.getStatus()});
        ingredientRequests.add(ingredientRequest);
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        ingredientRequests.remove(row);
    }

    public IngredientRequest getValueAt(int rowIndex) {
        return ingredientRequests.get(rowIndex);
    }
}
