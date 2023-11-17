package com.example.rest.controller.v2;

import com.example.rest.dto.OrderFilter;
import com.example.rest.dto.OrderListResponse;
import com.example.rest.dto.OrderResponse;
import com.example.rest.dto.UpsertOrderRequest;
import com.example.rest.dto.mapper.v2.OrderMapperV2;
import com.example.rest.model.Order;
import com.example.rest.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/order")
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderService orderService;
    private final OrderMapperV2 orderMapper;

    @GetMapping("/filter")
    public ResponseEntity<OrderListResponse> findAllByFilter(@Valid OrderFilter filter) {
        return ResponseEntity.ok(
                orderMapper.orderListToOrderListResponse(
                        orderService.filterBy(filter)));
    }

    @GetMapping
    public ResponseEntity<OrderListResponse> findAll() {
        return ResponseEntity.ok(orderMapper.orderListToOrderListResponse(
                orderService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderMapper.orderToResponse(orderService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid UpsertOrderRequest request) {
        Order newOrder = orderService.save(orderMapper.requestToOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderMapper.orderToResponse(newOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId, @RequestBody @Valid UpsertOrderRequest request) {
        Order updateOrder = orderService.update(orderMapper.requestToOrder(orderId, request));
        return ResponseEntity.ok(orderMapper.orderToResponse(updateOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
