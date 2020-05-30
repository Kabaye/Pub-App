package by.pub.bar.app.order.util;

import by.pub.bar.app.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDBProcessor {
    public Order toDB(Order Order) {
        long l = (long) (Order.getTotalPrice() * 1000);
        return Order.setTotalPrice((double) l);
    }

    public Order fromDB(Order Order) {
        return Order.setTotalPrice((long) (Order.getTotalPrice() / 10) / 100D);
    }
}
