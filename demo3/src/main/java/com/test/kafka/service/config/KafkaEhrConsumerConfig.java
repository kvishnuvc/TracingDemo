package com.test.kafka.service.config;


import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.spring.TracingConsumerFactory;
import io.opentracing.contrib.kafka.spring.TracingKafkaAspect;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaEhrConsumerConfig {

    private Environment environment;
    @Autowired
    private Tracer tracer;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Map<String, Object> getConfigProps(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty(KafkaConstants.BROKERS_PROPERTY));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return props;
    }

    public ConsumerFactory<String, String> consumerFactoryResponseItem() {
        Map<String, Object> configProps = getConfigProps("KafkaConstants.EHR_QUEUE_ITEM_GROUP_ID");
        // Decorate ConsumerFactory with TracingConsumerFactory
        return new TracingConsumerFactory<>(new DefaultKafkaConsumerFactory<>(configProps), tracer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactoryItem() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryResponseItem());
        factory.setConcurrency(Integer.valueOf(environment.getProperty(KafkaConstants.DEFAULT_CONCURRENCY)));
        factory.getContainerProperties().setPollTimeout(KafkaConstants.POLL_TIMEOUT);
        return factory;
    }

    // Use an aspect to decorate @KafkaListeners
    @Bean
    public TracingKafkaAspect tracingKafkaAspect() {
        return new TracingKafkaAspect(tracer);
    }
}
