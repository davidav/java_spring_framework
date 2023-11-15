package com.example.rest.repository;

import com.example.rest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBClientRepository extends JpaRepository<Client, Long> {
}
