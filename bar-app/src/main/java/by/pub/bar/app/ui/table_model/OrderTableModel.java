package by.pub.bar.app.ui.table_model;

import by.pub.bar.app.order.entity.Order;
import by.pub.bar.app.utils.Status;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class OrderTableModel extends DefaultTableModel {

    private static final Object[] ORDER_TABLE_HEADER = new String[]{"ID", "Client ID",
        "Price", "Status"};

    private List<Order> orders;

    public OrderTableModel() {
        super(ORDER_TABLE_HEADER, 0);
        orders = new ArrayList<>();
    }

    public void addRow(Order order) {
        super.addRow(
            new Object[]{order.getId(), order.getClientId(), order.getTotalPrice(),
                order.getStatus()});
        orders.add(order);
    }

    public Order getValueAt(int rowIndex) {
        return orders.get(rowIndex);
    }

    public int removeAcceptedRows() {
        int counter = 0;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getStatus().equals(Status.ACCEPTED)) {
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
        orders.remove(row);
    }
}


