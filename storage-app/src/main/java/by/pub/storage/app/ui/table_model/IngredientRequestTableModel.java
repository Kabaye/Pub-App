package by.pub.storage.app.ui.table_model;

import by.pub.storage.app.element.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.element.ingredient_request.entity.IngredientRequestStatus;
import org.springframework.stereotype.Component;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

@Component
public class IngredientRequestTableModel extends DefaultTableModel {

    private static final Object[] INGREDIENT_REQUEST_TABLE_HEADER = new String[]{"ID", "Name",
        "Amount", "Status"};

    private List<IngredientRequest> ingredientRequests;

    public IngredientRequestTableModel() {
        super(INGREDIENT_REQUEST_TABLE_HEADER, 0);
        ingredientRequests = new ArrayList<>();
    }

    public void addRow(IngredientRequest ingredientRequest) {
        super.addRow(
            new Object[]{ingredientRequest.getRequestId(), ingredientRequest.getIngredientName(),
                ingredientRequest.getIngredientAmount(), ingredientRequest.getStatus()});
        ingredientRequests.add(ingredientRequest);
    }

    public int removeAcceptedRows() {
        int counter = 0;
        for (int i = 0; i < ingredientRequests.size(); i++) {
            if (ingredientRequests.get(i).getStatus().equals(IngredientRequestStatus.ACCEPTED)) {
                removeRow(i);
                i--;
                counter++;
            }
        }
        return counter;
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
