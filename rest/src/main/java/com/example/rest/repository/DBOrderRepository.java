package com.example.rest.repository;

import com.example.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBOrderRepository extends JpaRepository<Order, Long> {
}
