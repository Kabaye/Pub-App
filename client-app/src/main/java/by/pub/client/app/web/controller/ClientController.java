package by.pub.client.app.web.controller;

import by.pub.client.app.service.ClientService;
import by.pub.client.app.web.converter.OrderConverter;
import by.pub.client.app.web.dto.OrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;
    private final OrderConverter orderConverter;

    public ClientController(ClientService clientService, OrderConverter orderConverter) {
        this.clientService = clientService;
        this.orderConverter = orderConverter;
    }

    @PostMapping("/accept-order")
    public void handleAcceptedOrder(@RequestBody OrderDTO acceptedOrderDTO) {
        clientService.handleAcceptedOrder(orderConverter.toEntity(acceptedOrderDTO));
    }
}
