package com.example.rest.repository;

import com.example.rest.model.Client;
import com.example.rest.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();

    Optional<Order> findById(Long id);

    Client save(Order order);

    Client update(Order order);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
}
