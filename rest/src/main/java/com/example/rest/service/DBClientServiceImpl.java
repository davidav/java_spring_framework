package com.example.rest.service;

import com.example.rest.aop.Loggable;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.repository.DBClientRepository;
import com.example.rest.repository.DBOrderRepository;
import com.example.rest.util.AppUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class DBClientServiceImpl implements ClientService {

    private final DBClientRepository clientRepository;
    private final DBOrderRepository orderRepository;

    @Override
    @Loggable
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Клиент с id {} не найден", id
                )));
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        Client existedClient = findById(client.getId());
        AppUtils.copyNonNullProperties(client, existedClient);

        return clientRepository.save(existedClient);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Loggable
    public Client saveWithOrders(Client client, List<Order> orders) {
        Client savedClient = clientRepository.save(client);

//        if(true) throw new RuntimeException(); for example transactional and aspect

        for (Order order : orders) {
            order.setClient(savedClient);
            var savedOrder = orderRepository.save(order);
            savedClient.addOrder(savedOrder);
        }
        return savedClient;
    }
}
