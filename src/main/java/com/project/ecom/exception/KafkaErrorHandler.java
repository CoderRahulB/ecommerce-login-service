package com.project.ecom.exception;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaErrorHandler implements ProducerListener<String, Object> {

	public void onError(
        ProducerRecord<String, Object> record,
        Exception exception,
         RecordMetadata metadata
    ) {
        log.error("""
            Failed to send Kafka message:
            Topic: {}
            Key: {}
            Payload: {}
            Error: {}""",
            record.topic(),
            record.key(),
            record.value(),
            exception.getMessage()
        );
        
	}
	
	
    public boolean isInterestedInSuccess() {
        return false; 
    }

}
