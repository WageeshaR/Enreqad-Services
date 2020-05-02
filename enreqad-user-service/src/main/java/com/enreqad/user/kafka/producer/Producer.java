package com.enreqad.user.kafka.producer;

import com.enreqad.user.kafka.producer.json.SerializableObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "newuser";
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, SerializableObject> kafkaTemplate;

    public void sendMessage(SerializableObject object) {

        objectMapper = new ObjectMapper();
        ListenableFuture<SendResult<String, SerializableObject>> future = kafkaTemplate.send(TOPIC, object);

        future.addCallback(new ListenableFutureCallback<SendResult<String, SerializableObject>>() {

            @Override
            public void onSuccess(SendResult<String, SerializableObject> result) {
                try {
                    System.out.println("Sent message=[" + objectMapper.writeValueAsString(object) + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    logger.info(String.format("$$ -> Producing message --> %s",objectMapper.writeValueAsString(object)));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable ex) {
                try {
                    System.out.println("Unable to send message=[" + objectMapper.writeValueAsString(object) + "] due to : " + ex.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}