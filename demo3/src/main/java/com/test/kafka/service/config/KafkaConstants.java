package com.test.kafka.service.config;

public final class KafkaConstants {
    private KafkaConstants() {
        super();
    }
    public static final String BROKERS_PROPERTY = "kafka.server";
    public static final String DEFAULT_CONCURRENCY = "kafka.defaultConcurrency";
    public static final Long POLL_TIMEOUT = 3000L;
}
