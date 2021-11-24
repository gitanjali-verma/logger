
package com.digitaldots.logger.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.digitaldots.logger.entity.Entity;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;

@Component

@Slf4j
public class Listener {

    @Autowired
    private MessageProducer messageProducer;

    @Value("${topic.notification.activity}")
    private String notificationTopicActivity;

    @Autowired
    Tracer tracer;

    @KafkaListener(topics = "${topic.notification}")
    @SendTo("${topic.notification.activity}")
    private void listen(@Payload Entity object, @org.springframework.messaging.handler.annotation.Headers Map<String, Object> headers) {
        log.info("active span from listener is : {}", tracer.activeSpan());
        Map<String, String> strHeaders = new HashMap<>();
        for (Entry<String, Object> entry : headers.entrySet()) {
            if (entry.getKey().contains("traceId") || entry.getKey().contains("spanId")) {
                strHeaders.put(entry.getKey(), new String((byte[]) entry.getValue()));
            } else {
                strHeaders.put(entry.getKey(), entry.getValue().toString());
            }
        }

        System.out.println(strHeaders);

        Span span = tracer.buildSpan(UUID.randomUUID().toString()).start();
        span.setBaggageItem("traceId", strHeaders.get("traceId"));
        span.setBaggageItem("spanId", strHeaders.get("spanId"));

        tracer.activateSpan(span);
        log.info("active span from listener is : {}", tracer.activeSpan());

//        SpanContext spanContext = tracer
//            .extract(Format.Builtin.TEXT_MAP, new TextMapExtractAdapter(strHeaders));

//      try( Scope activeScope = tracer.buildSpan("new_span").asChildOf(spanContext).startActive(true)) {
//        ResponseEntity response = restTemplate.getForEntity(restEndPoint, Void.class);
//      }

        log.info("listener");
        messageProducer.sendMessage(notificationTopicActivity, object);
    }

    @KafkaListener(topics = "${topic.notification.activity}")
    private void listenTest(Entity object) {
        log.info("active span from listener is : {}", tracer.activeSpan());
        log.info("listener activity test");
    }

//    @KafkaListener(topics = "${topic.notification}")
//    @SendTo("${topic.notification.activity}")
    public Message<String> listen(Message<String> in) {
        System.out.println(in);
        Headers nativeHeaders = in.getHeaders().get(KafkaHeaders.NATIVE_HEADERS, Headers.class);
        byte[] replyTo = nativeHeaders.lastHeader(KafkaHeaders.REPLY_TOPIC).value();
        byte[] correlation = nativeHeaders.lastHeader(KafkaHeaders.CORRELATION_ID).value();
        return MessageBuilder.withPayload(in.getPayload().toUpperCase()).setHeader("myHeader", nativeHeaders.lastHeader("myHeader").value())
            .setHeader(KafkaHeaders.CORRELATION_ID, correlation).setHeader(KafkaHeaders.TOPIC, replyTo).build();
    }

}
