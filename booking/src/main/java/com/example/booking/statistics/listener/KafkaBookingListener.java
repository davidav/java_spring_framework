package com.example.booking.statistics.listener;

import com.example.booking.statistics.model.KafkaBooking;
import com.example.booking.statistics.service.StatisticsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaBookingListener {

    private final StatisticsServiceImpl statisticsService;

    @KafkaListener(topics = "${app.kafka.kafkaBookingTopic}",
            groupId = "${app.kafka.kafkaBookingGroupId}",
            containerFactory = "kafkaBookingConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload KafkaBooking kafkaBooking,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received booking: {}", kafkaBooking);
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);

        statisticsService.addBooking(kafkaBooking);
    }

}
