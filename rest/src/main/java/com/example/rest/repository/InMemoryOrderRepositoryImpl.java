package com.example.rest.repository;

import com.example.rest.exception.AppHelperException;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryOrderRepositoryImpl implements OrderRepository {

    private ClientRepository clientRepository;

    private final Map<Long, Order> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);


    @Override
    public List<Order> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Order save(Order order) {
        Long orderId = currentId.getAndIncrement();
        Long clientId = order.getClient().getId();
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new AppHelperException(MessageFormat.format("Client with ID: {} not found", clientId))
        );
        order.setClient(client);
        order.setId(orderId);
        Instant now = Instant.now();
        order.setCreateAt(now);
        order.setUpdateAt(now);

        repository.put(orderId, order);
        client.addOrder(order);
        clientRepository.update(client);

        return order;
    }

    @Override
    public Order update(Order order) {
        Order currentOrder = repository.get(order.getId());
        if (currentOrder == null) {
            throw new AppHelperException(MessageFormat.format("Order with ID: {} not found", order.getId()));
        }
        AppUtils.copyNonNullProperties(order, currentOrder);
        Instant now = Instant.now();
        currentOrder.setUpdateAt(now);

        repository.put(currentOrder.getId(), currentOrder);

        return currentOrder;
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

}
