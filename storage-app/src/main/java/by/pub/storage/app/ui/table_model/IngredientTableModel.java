package by.pub.storage.app.ui.table_model;

import by.pub.storage.app.ingredient.entity.Ingredient;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class IngredientTableModel extends DefaultTableModel {

    List<Ingredient> ingredients;

    public IngredientTableModel(List<Ingredient> list, Object[] columnNames,
        int rowCount) {
        super(columnNames, rowCount);
        ingredients = list;
        for (Ingredient ingredientRequest : list) {
            addRow(new Object[]{
                ingredientRequest.getName(),
                ingredientRequest.getAmount()});
        }
    }

    public void addRow(Ingredient ingredient) {
        super.addRow(
            new Object[]{
                ingredient.getName(),
                ingredient.getAmount()});
        ingredients.add(ingredient);
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        ingredients.remove(row);
    }

    public void removeRow(Ingredient ingredient) {
        int index = getIndexByName(ingredient.getName());
        removeRow(index);
    }

    public Ingredient getValueAt(int rowIndex) {
        return ingredients.get(rowIndex);
    }

    private int getIndexByName(String name) {
        for (int i = 0; i < ingredients.size(); i++) {
            if ((name.compareTo(ingredients.get(i).getName()) == 0)) {
                return i;
            }
        }
        return -1;
    }
}
