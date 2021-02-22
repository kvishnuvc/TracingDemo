package com.test.kafka.service.message;

import org.springframework.messaging.MessageHeaders;

public interface MessageListener<T> {
    public void listen(T message, MessageHeaders messageHeaders);
}
