package com.digitaldots.logger.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digitaldots.logger.config.MessageProducer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JaegerServerService {

    @Value("${topic.notification}")
    private String notificationTopic;

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    MessageProducer messageProducer;

    public Entity get(String id) {
        Entity entity = entityRepository.findById(id).get();
        log.info("entity is: {}", entity);
        messageProducer.sendMessage(notificationTopic, entity);
        return entity;
    }
}