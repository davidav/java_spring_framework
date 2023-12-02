package com.exemple.integration_client.repo;

import com.exemple.integration_client.entity.DataBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataBaseEntityRepository extends JpaRepository<DataBaseEntity, UUID> {



}
