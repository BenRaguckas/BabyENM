package com.goTeam.BabyENM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private static final String TOPIC_NAME = "TEST";
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    public void sendMessage(String key, String value){
        this.kafkaTemplate.send(TOPIC_NAME, key, value);
    }
}
