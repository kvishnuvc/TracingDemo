package com.test.trace2.demo2.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

@Component
public class RequestDataConsumer implements MessageListener {
    final String responseTopic = "com.vrp.response.topic";

    @Autowired
    ResponseDataProducer producer;


    @KafkaListener(topics = responseTopic, id = "RequestDataConsumer", containerFactory = "kafkaListenerContainerFactoryRequestItem", autoStartup = "true")
    public void listen(String message, @Headers MessageHeaders messageHeaders) {
        System.out.println("Messaged received - "+ message);

    }
}