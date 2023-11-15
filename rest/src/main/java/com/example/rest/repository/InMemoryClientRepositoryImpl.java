package com.example.rest.repository;


import com.example.rest.exception.AppHelperException;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryClientRepositoryImpl implements ClientRepository {

    private final Map<Long, Client> repository = new ConcurrentHashMap<>();
    private OrderRepository orderRepository;
    private final AtomicLong currentId = new AtomicLong(1);


    @Override
    public List<Client> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Client save(Client client) {
        Long id = currentId.getAndIncrement();
        client.setId(id);
        repository.put(client.getId(), client);

        return client;
    }

    @Override
    public Client update(Client client) {
        Client currentClient = repository.get(client.getId());
        if (currentClient == null){
            throw new AppHelperException(MessageFormat.format("Client with ID: {} not found", client.getId()));
        }
        AppUtils.copyNonNullProperties(client, currentClient);
        currentClient.setId(client.getId());
        repository.put(currentClient.getId(), currentClient);
        return currentClient;
    }

    @Override
    public void deleteById(Long id) {
        Client client = repository.get(id);
        if ( client == null){
            throw new AppHelperException(MessageFormat.format("Client with ID: {} not found", id));
        }

        orderRepository.deleteByIdIn(client.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

}
