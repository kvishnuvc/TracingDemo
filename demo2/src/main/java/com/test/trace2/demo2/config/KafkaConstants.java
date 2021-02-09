package com.test.trace2.demo2.config;

public final class KafkaConstants {
    private KafkaConstants() {
        super();
    }
    public static final String BROKERS_PROPERTY = "kafka.server";
    public static final String DEFAULT_CONCURRENCY = "kafka.defaultConcurrency";
    public static final String EHR_QUEUE_ITEM_GROUP_ID = "ehrQueueItemListener";
    public static final String EHR_QUEUE_ITEM_HANDLER_GROUP_ID = "ehrQueueItemHandlerListener";
    public static final Long POLL_TIMEOUT = 3000L;
    public static final String TRACER_SPAN = "tracer_Span";

}
