package by.pub.bar.app.ui.table_model;

import by.pub.bar.app.order.entity.Order;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

// TODO: 5/30/20 Check all methods
@Component
public class OrderTableModel extends DefaultTableModel {

    private static final Object[] ORDER_TABLE_HEADER = new String[]{"ID", "Name",
        "Amount", "Status"};

    private List<Order> orders;

    public OrderTableModel() {
        super(ORDER_TABLE_HEADER, 0);
        orders = new ArrayList<>();
    }
}


