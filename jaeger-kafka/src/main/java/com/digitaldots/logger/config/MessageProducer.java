package com.digitaldots.logger.config;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitaldots.logger.entity.Entity;

import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;

/**
 * Component to send message to kafka topics.
 * 
 * @author msrsr
 *
 */
@Component
@Transactional
@Slf4j
public class MessageProducer {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    Tracer tracer;

    public void sendMessage(String topicName, Entity object) {
        try {
            log.info("sending message to kafka to topic : {}", topicName);
            log.info("active span from producer is : {}", tracer.activeSpan());
//            log.info("traceId is : {}", tracer.activeSpan().context().toTraceId());
            ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(topicName, object);
            log.debug("before sending message...");
            producerRecord.headers().add("traceId", tracer.activeSpan().context().toTraceId().getBytes());
            producerRecord.headers().add("spanId", tracer.activeSpan().context().toSpanId().getBytes());
            kafkaTemplate.send(producerRecord);
            log.debug("after sending message...");
        } catch (KafkaException e) {
            log.error("Unable to send message to kafka", e.getMessage());
            throw new KafkaException(e);
        }
    }
}
