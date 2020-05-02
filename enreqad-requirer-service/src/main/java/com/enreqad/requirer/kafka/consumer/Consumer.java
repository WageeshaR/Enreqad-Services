package com.enreqad.requirer.kafka.consumer;

import com.enreqad.requirer.entity.ReqUser;
import com.enreqad.requirer.kafka.consumer.json.SerializableObject;
import com.enreqad.requirer.repository.ReqUserRepository;
import com.enreqad.requirer.util.GlobalConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private ObjectMapper objectMapper;

    @Autowired
    private ReqUserRepository reqUserRepository;

    @KafkaListener(topics = GlobalConstants.KAFKA_TOPIC_NEW_USER, groupId = "group_id")
    public void consumeAndCreateUser(SerializableObject object)
    {
        objectMapper = new ObjectMapper();

        try {
            logger.info(String.format("$$ -> Consumed Message -> %s",objectMapper.writeValueAsString(object)));
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        if (object.getMessage().equals(GlobalConstants.REQUIRER_SERVICE))
        {
            try {
                reqUserRepository.save(
                        new ReqUser(
                                Long.parseLong(object.getDataMap().get(GlobalConstants.USER_PROFILE_USERID)),
                                String.valueOf(object.getDataMap().get(GlobalConstants.USER_PROFILE_USERNAME)),
                                String.valueOf(object.getDataMap().get(GlobalConstants.USER_PROFILE_FULLNAME))
                        )
                );
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Failed to create user profile");
            }
        }
    }
}