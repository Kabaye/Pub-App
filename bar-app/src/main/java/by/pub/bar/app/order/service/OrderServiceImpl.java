package by.pub.bar.app.order.service;

import by.pub.bar.app.event.entity.NewOrderSavedEvent;
import by.pub.bar.app.event.publisher.BarEventPublisher;
import by.pub.bar.app.order.entity.Order;
import by.pub.bar.app.order.repository.OrderRepository;
import by.pub.bar.app.order.utils.OrderDBProcessor;
import by.pub.bar.app.utils.Status;
import by.pub.bar.app.websocket.server.message_sender.OrderWebSocketMessageSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDBProcessor processor;
    private final OrderWebSocketMessageSender orderWebSocketMessageSender;
    private final BarEventPublisher publisher;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDBProcessor processor, OrderWebSocketMessageSender orderWebSocketMessageSender, BarEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.processor = processor;
        this.orderWebSocketMessageSender = orderWebSocketMessageSender;
        this.publisher = publisher;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(processor::fromDB)
                .collect(Collectors.toList());
    }

    @Override
    public Order findById(String id) {
        return processor.fromDB(orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no Order with id: " + id)));
    }

    @Override
    public Order saveOrder(Order order) {
        final Order savedOrder = processor.fromDB(orderRepository.save(processor.toDB(processor.preprocessTotalPrice(order))));
        publisher.publishEvent(new NewOrderSavedEvent(savedOrder));
        return savedOrder;
    }

    @Override
    public void deleteOrderById(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order acceptOrder(String id) {
        Order order = findById(id);
        orderWebSocketMessageSender.sendAcceptedOrder(order.setStatus(Status.ACCEPTED));
        orderRepository.deleteById(id);
        return order;
    }
}
