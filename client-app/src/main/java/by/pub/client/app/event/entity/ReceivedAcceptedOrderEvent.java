package by.pub.client.app.event.entity;

import by.pub.client.app.order.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReceivedAcceptedOrderEvent extends ClientAppEvent {

    private Order order;
}
