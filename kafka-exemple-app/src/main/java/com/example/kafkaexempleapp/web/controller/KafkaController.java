package com.example.kafkaexempleapp.web.controller;

import com.example.kafkaexempleapp.model.KafkaMessage;
import com.example.kafkaexempleapp.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    private final KafkaMessageService kafkaMessageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody KafkaMessage message){
        kafkaTemplate.send(topicName, message);

        return ResponseEntity.ok("Message send to kafka");
    }

    @GetMapping("/{id}")
    public ResponseEntity<KafkaMessage> getById(@PathVariable Long id){
        KafkaMessage body = kafkaMessageService.getById(id).orElseThrow();
        return ResponseEntity.ok(body);
    }


}
