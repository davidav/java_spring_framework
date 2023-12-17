package com.example.orderstatusservice.listener;


import com.example.orderservice.model.OrderEvent;
import com.example.orderservice.model.StatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    @Value("${app.kafka.kafkaOrderStatusTopic}")
    private String topicName;
    private final KafkaTemplate<String, StatusEvent> kafkaTemplate;


    @KafkaListener(topics = "${app.kafka.kafkaOrderTopic}",
            groupId = "${app.kafka.kafkaOrderGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listen() {
        kafkaTemplate.send(topicName, StatusEvent.builder().status("CREATED").date(Instant.now()).build());
    }
}
