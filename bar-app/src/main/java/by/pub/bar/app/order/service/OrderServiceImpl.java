package by.pub.bar.app.order.service;

import by.pub.bar.app.order.entity.Order;
import by.pub.bar.app.order.repository.OrderRepository;
import by.pub.bar.app.order.util.OrderDBProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDBProcessor processor;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDBProcessor processor) {
        this.orderRepository = orderRepository;
        this.processor = processor;
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
        return orderRepository.save(processor.toDB(order));
    }

    @Override
    public void deleteOrderById(String id) {
        orderRepository.deleteById(id);
    }
}
