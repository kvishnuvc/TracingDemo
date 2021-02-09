package com.test.kafka.service.message;

import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

@Component
public class RequestDataListener {
    final String topic = "com.vrp.request.topic";

    Tracer tracer;

    @Autowired
    DataProducer producer;

    public RequestDataListener(Tracer tracer) {
        this.tracer = tracer;
    }

    @KafkaListener(topics = topic, id = "RequestDataListener-Demo3", containerFactory = "kafkaListenerContainerFactoryItem")
    public void listen(String message, @Headers MessageHeaders messageHeaders) {
        tracer.scopeManager().activeSpan().log("EHR: Received response Message - " + message);
        producer.sendRequestMessage("Message Response for Main service ->" + message);
    }
}
