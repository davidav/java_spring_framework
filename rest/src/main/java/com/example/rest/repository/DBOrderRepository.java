package com.example.rest.repository;

import com.example.rest.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DBOrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Page<Order> findAllByProduct(String product, Pageable pageable);


//      example HQL-query
//    @Query("SELECT o FROM com.example.rest.model.Order o WHERE o.product = :productName")
//    List<Order> getByProduct(String productName);


//      example SQL-query
//    @Query(value = "SELECT o FROM orders o WHERE o.product = :productName", nativeQuery = true)
//    List<Order> getByProduct(String productName);
}
