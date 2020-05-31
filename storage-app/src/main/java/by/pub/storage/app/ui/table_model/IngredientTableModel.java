package by.pub.storage.app.ui.table_model;

import by.pub.storage.app.element.ingredient.entity.Ingredient;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class IngredientTableModel extends DefaultTableModel {

    private static final Object[] INGREDIENT_TABLE_HEADER = new String[]{"Name", "Amount"};

    private final List<Ingredient> ingredients;

    public IngredientTableModel() {
        super(INGREDIENT_TABLE_HEADER, 0);
        ingredients = new ArrayList<>();
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
        if (index >= 0) {
            removeRow(index);
        }
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
