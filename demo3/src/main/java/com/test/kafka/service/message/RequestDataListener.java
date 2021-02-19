package com.test.kafka.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

@Component
public class RequestDataListener implements MessageListener {
    final String topic = "com.vrp.request.topic";


    @Autowired
    DataProducer producer;


    @KafkaListener(topics = topic, id = "RequestDataListener-Demo3", containerFactory = "kafkaListenerContainerFactoryItem")
    public void listen(String message, @Headers MessageHeaders messageHeaders) {
        producer.sendRequestMessage("Message Response for Main service ->" + message);
    }
}
