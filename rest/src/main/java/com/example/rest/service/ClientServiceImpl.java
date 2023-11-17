package com.example.rest.service;

import com.example.rest.exception.AppHelperException;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;


    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new AppHelperException(MessageFormat.format("Client with ID: {} not found", id))
        );
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);

    }

    @Override
    public Client saveWithOrders(Client client, List<Order> orders) {
        throw new NotImplementedException();
    }
}
