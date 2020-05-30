package com.enreqad.enquirer.controller;

import com.enreqad.enquirer.controller.payload.ApiResponse;
import com.enreqad.enquirer.controller.payload.EnqUserRequest;
import com.enreqad.enquirer.entity.EnqUser;
import com.enreqad.enquirer.entity.topic.BaseTopic;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import com.enreqad.enquirer.repository.BaseTopicRepository;
import com.enreqad.enquirer.repository.EnqUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class EnqUserController {

    @Autowired
    private EnqUserRepository enqUserRepository;

    @Autowired
    private BaseTopicRepository baseTopicRepository;

    //    Below method not in use    //

    @PostMapping("/create")
    public ResponseEntity<?> createEnqUser(@RequestBody EnqUserRequest enqUserRequest)
    {
        try {

            EnqUser enqUser = new EnqUser(
                    enqUserRequest.getUserId(),
                    enqUserRequest.getUsername()
            );
            enqUserRepository.save(enqUser);

            return new ResponseEntity(new ApiResponse(true, "User" + enqUserRequest.getUsername() + " created for Enquirer module with user-id: " + enqUserRequest.getUserId()), HttpStatus.OK);

        } catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity( new ApiResponse(false, "Could not create Enquirer User for user-id: " + enqUserRequest.getUserId()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profile/topics")
    public ResponseEntity<?> subscribeTopics(@RequestHeader("user-id") long userId, @RequestBody List<BaseTopicEnum> topicEnums)
    {
        try {

            EnqUser user = enqUserRepository.findById(userId).orElseThrow( () -> new Exception("User not found") );
            for ( BaseTopicEnum topicEnum : topicEnums )
            {
                BaseTopic topic = baseTopicRepository.findByName(topicEnum).orElseThrow( () -> new Exception("Enum type not found") );
                user.getBaseTopics().add(topic);
            }
            enqUserRepository.flush();
            return new ResponseEntity(new ApiResponse(true, "Topics subscribed successfully for user-id: " + userId), HttpStatus.OK);

        } catch ( Exception e )
        {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Failed request to subscribe topics for user-id: " + userId), HttpStatus.BAD_REQUEST);
        }
    }

}
