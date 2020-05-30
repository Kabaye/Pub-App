package by.pub.bar.app.order.utils;

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

    public Order preprocessTotalPrice(Order order) {
        order.getProducts().forEach(product -> order.setTotalPrice(order.getTotalPrice() + product.getPrice()));
        return order;
    }
}
