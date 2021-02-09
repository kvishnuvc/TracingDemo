package com.test.kafka.service.message;

import com.google.common.collect.ImmutableMap;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataProducer {
    final String reqTopic = "com.vrp.response.topic";

    @Autowired
    Tracer tracer;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendRequestMessage(String str) {


        MessageBuilder<String> messageBuilder = MessageBuilder.withPayload(str);
        messageBuilder.setHeader(KafkaHeaders.TOPIC, reqTopic);

        Map<String, String> spanMap = new HashMap<>();

        tracer.scopeManager().activeSpan().log(ImmutableMap.of(Fields.MESSAGE, "Send Request - " + str));
        kafkaTemplate.send(messageBuilder.build()).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                tracer.scopeManager().activeSpan().log(ImmutableMap.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, throwable));
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                tracer.scopeManager().activeSpan().log(ImmutableMap.of(Fields.MESSAGE, "Successfully Send request message"));
            }
        });

    }
}
