package com.test.kafka.service.config;

import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.spring.TracingProducerFactory;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaEhrProducerConfig {

    private Environment environment;
    @Autowired
    private Tracer tracer;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Map<String, Object> getConfigProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty(KafkaConstants.BROKERS_PROPERTY));
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, String> tcTestSendProducerFactory() {
        return new TracingProducerFactory<>(new DefaultKafkaProducerFactory<>(getConfigProps()), tracer);
    }

    @Bean
    public KafkaTemplate<String, String> tcTestSendKafkaTemplate() {
        return new KafkaTemplate<>(tcTestSendProducerFactory());
    }
}
