package com.test.kafka.service.message;

import org.springframework.messaging.MessageHeaders;

public interface MessageListener {
    public void listen(String message, MessageHeaders messageHeaders);
}
