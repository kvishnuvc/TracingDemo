package com.test.trace2.demo2.message;

import org.springframework.messaging.MessageHeaders;

public interface MessageListener {
    public void listen(String message, MessageHeaders messageHeaders);
}
