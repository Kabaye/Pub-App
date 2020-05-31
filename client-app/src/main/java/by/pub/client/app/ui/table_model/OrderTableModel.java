package by.pub.client.app.ui.table_model;

import by.pub.client.app.order.entity.Order;
import by.pub.client.app.order.entity.Status;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class OrderTableModel extends DefaultTableModel {

    private static final Object[] ORDER_TABLE_HEADER = new String[]{"Price", "Status"};

    private final List<Order> orders;

    public OrderTableModel() {
        super(ORDER_TABLE_HEADER, 0);
        orders = new ArrayList<>();
    }

    public void addRow(Order order) {
        super.addRow(
            new Object[]{order.getTotalPrice(),
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

    public void removeAllRows() {
        for (int i = 0; i < orders.size(); i++) {
            removeRow(0);
        }
    }

    public void removeRow(Order order) {
        int index = getIndexById(order.getOrderId());
        if (index >= 0) {
            removeRow(index);
        }
    }

    private int getIndexById(String id) {
        for (int i = 0; i < orders.size(); i++) {
            if ((id.compareTo(orders.get(i).getOrderId()) == 0)) {
                return i;
            }
        }
        return -1;
    }
}