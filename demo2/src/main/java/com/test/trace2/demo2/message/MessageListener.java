package com.test.trace2.demo2.message;

import org.springframework.messaging.MessageHeaders;

public interface MessageListener<T> {
    public void listen(T message, MessageHeaders messageHeaders);
}
