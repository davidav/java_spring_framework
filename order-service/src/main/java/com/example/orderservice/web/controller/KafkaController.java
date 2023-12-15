package com.example.orderservice.web.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaOrderTopic}")
    private String topicName;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Order order){

        kafkaTemplate.send(topicName,
                OrderEvent.builder()
                        .product(order.getProduct())
                        .quantity(order.getQuantity())
                        .build());

        return ResponseEntity.ok("Message send to kafka");
    }

}
