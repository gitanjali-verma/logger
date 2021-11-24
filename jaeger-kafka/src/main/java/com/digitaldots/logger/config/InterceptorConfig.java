package com.digitaldots.logger.config;

import java.util.Collections;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SimpleKafkaHeaderMapper;
import org.springframework.kafka.support.converter.MessagingMessageConverter;

import io.opentracing.contrib.kafka.TracingConsumerInterceptor;
import io.opentracing.contrib.kafka.TracingProducerInterceptor;

@Configuration
public class InterceptorConfig {

    @Autowired
    KafkaProperties properties;

    @Bean
    public ProducerFactory<Object, Object> kafkaProducerFactory() {
        Map<String, Object> producerProperties = properties.buildProducerProperties();
        producerProperties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, TracingProducerInterceptor.class.getName());
        DefaultKafkaProducerFactory<Object, Object> factory = new DefaultKafkaProducerFactory<>(producerProperties);
        String transactionIdPrefix = properties.getProducer().getTransactionIdPrefix();
        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        return factory;
    }

    @Bean
    public ConsumerFactory<Object, Object> kafkaConsumerFactory() {
        Map<String, Object> consumerProperties = properties.buildConsumerProperties();
        consumerProperties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, Collections.singletonList(TracingConsumerInterceptor.class));
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate(ProducerFactory<Object, Object> kafkaProducerFactory) {
        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        MessagingMessageConverter messageConverter = new MessagingMessageConverter();
        messageConverter.setHeaderMapper(new SimpleKafkaHeaderMapper("*"));
        kafkaTemplate.setMessageConverter(messageConverter);
        return kafkaTemplate;
    }
    
//    @Bean
//    public ApplicationRunner runner(ReplyingKafkaTemplate<String, String, String> template) {
//        return args -> {
//            Headers headers = new RecordHeaders();
//            headers.add(new RecordHeader("myHeader", "myHeaderValue".getBytes()));
//            headers.add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "so55622224.replies".getBytes())); // automatic in 2.2
//            ProducerRecord<String, String> record = new ProducerRecord<>("so55622224", null, null, "foo", headers);
//            RequestReplyFuture<String, String, String> future = template.sendAndReceive(record);
//            ConsumerRecord<String, String> reply = future.get();
//            System.out.println("Reply: " + reply.value() + " myHeader="
//                    + new String(reply.headers().lastHeader("myHeader").value()));
//        };
//    }
}
