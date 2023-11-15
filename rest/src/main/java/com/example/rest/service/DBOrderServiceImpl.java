package com.example.rest.service;


import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.repository.DBOrderRepository;
import com.example.rest.util.AppUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class DBOrderServiceImpl implements OrderService {

    private final DBOrderRepository orderRepository;
    private final ClientService clientService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Заказ с id {} не наден", id)
        ));
    }

    @Override
    public Order save(Order order) {
        Client client = clientService.findById(order.getClient().getId());
        order.setClient(client);

        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        Client client = clientService.findById(order.getClient().getId());
        Order existedOrder = findById(order.getId());
        AppUtils.copyNonNullProperties(order, existedOrder);
        existedOrder.setClient(client);

        return orderRepository.save(existedOrder);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        orderRepository.deleteAllByIdInBatch(ids);

    }
}
