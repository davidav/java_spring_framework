package com.example.rest.service;

import com.example.rest.model.Client;
import com.example.rest.repository.DBClientRepository;
import com.example.rest.util.AppUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DBClientServiceImpl implements ClientService{

    private final DBClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Клиент с id {} не наден", id
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
}
