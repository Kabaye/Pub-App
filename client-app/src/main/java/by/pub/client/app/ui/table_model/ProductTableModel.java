package by.pub.client.app.ui.table_model;

import by.pub.client.app.product.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class ProductTableModel extends DefaultTableModel {

    private static final Object[] PRODUCT_TABLE_HEADER = new String[]{"Name", "Price"};

    private final List<Product> products;

    public ProductTableModel() {
        super(PRODUCT_TABLE_HEADER, 0);
        products = new ArrayList<>();
    }

    public void addRow(Product product) {
        super.addRow(
            new Object[]{product.getName(),
                product.getPrice()});
        products.add(product);
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        products.remove(row);
    }

    public List<Product> getValues(int[] rowIndexes) {
        List<Product> indexedProducts = new ArrayList<>();
        for (int rowIndex : rowIndexes) {
            indexedProducts.add(products.get(rowIndex));
        }
        return indexedProducts;
    }

}
