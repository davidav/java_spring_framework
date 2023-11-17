package com.example.rest.repository;

import com.example.rest.dto.OrderFilter;
import com.example.rest.model.Order;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;

public interface OrderSpecification {

    static Specification<Order> withFilter(OrderFilter orderFilter){
        return Specification.where(byProductName(orderFilter.getProductName()))
                .and(byCostRange(orderFilter.getMinCost(), orderFilter.getMaxCost()))
                .and(buClientId(orderFilter.getClientId()))
                .and(byCreatedAtBefore(orderFilter.getCreateBefore()))
                .and(byUpdateAtBefore(orderFilter.getUpdateBefore()));
    }

    static Specification<Order> byUpdateAtBefore(Instant updateBefore) {
        return ((root, query, criteriaBuilder) -> {
            if (updateBefore == null){
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("updateAt"), updateBefore);
        });
    }

    static Specification<Order> byCreatedAtBefore(Instant createBefore) {
        return ((root, query, criteriaBuilder) -> {
            if (createBefore == null){
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createBefore);
        });

    }

    static Specification<Order> buClientId(Long clientId) {
        return (root, query, criteriaBuilder) -> {
            if (clientId == null){
                return null;
            }
            return criteriaBuilder.equal(root.get("client").get("id"), clientId);
        };
    }

    static Specification<Order> byCostRange(BigDecimal minCost, BigDecimal maxCost) {
        return (root, query, cb) -> {
            if (minCost == null && maxCost == null){
                return null;
            }
            if (minCost == null){
                return cb.lessThanOrEqualTo(root.get("cost"), maxCost);
            }
            if (maxCost == null){
                return cb.greaterThanOrEqualTo(root.get("cost"), minCost);
            }
            return cb.between(root.get("cost"), minCost, maxCost);
        };
    }

    static Specification<Order> byProductName(String productName) {
        return (root, query, cb) -> {
            if (productName == null){
                return null;
            }
            return cb.equal(root.get("product"), productName);
        };
    }


}
