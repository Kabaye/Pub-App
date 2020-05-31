package by.pub.bar.app.event.entity;

import by.pub.bar.app.element.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewOrderSavedEvent extends BarAppEvent {

    private Order order;
}
