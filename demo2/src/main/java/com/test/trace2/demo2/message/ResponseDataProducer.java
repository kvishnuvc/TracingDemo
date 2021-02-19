package com.test.trace2.demo2.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class ResponseDataProducer {
    final String responseTopic = "com.vrp.request.topic";


    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String str) {
        MessageBuilder<String> messageBuilder = MessageBuilder.withPayload(str);
        messageBuilder.setHeader(KafkaHeaders.TOPIC, responseTopic);

        kafkaTemplate.send(messageBuilder.build()).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
            }
        });

    }
}
