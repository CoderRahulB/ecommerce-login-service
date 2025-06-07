package com.project.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;

@Configuration
public class KafkaConfig {
	@Bean
    public KafkaTemplate<String, Object> kafkaTemplate(
            ProducerFactory<String, Object> producerFactory,
            ProducerListener<String, Object> producerListener) {
        KafkaTemplate<String, Object> template = new KafkaTemplate<>(producerFactory);
        template.setProducerListener(producerListener);
        return template;
    }

    @Bean
    public ProducerListener<String, Object> producerListener() {
        return new LoggingProducerListener<>();
    }

}
