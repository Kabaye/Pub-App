package by.pub.bar.app.ui.table;

import by.pub.bar.app.utils.Status;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class OrderTable extends JTable {

    private HashMap<Integer, Supplier<Class<?>>> map = new HashMap<>();

    public OrderTable(TableModel dm) {
        super(dm);

        map.put(0, () -> String.class);
        map.put(1, () -> String.class);
        map.put(2, () -> Long.class);
        map.put(3, () -> Status.class);

    }

    @Override
    public Class<?> getColumnClass(int column) {
        final Supplier<Class<?>> classSupplier = map.get(column);
        if (Objects.isNull(classSupplier)) {
            return String.class;
        }
        return classSupplier.get();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
