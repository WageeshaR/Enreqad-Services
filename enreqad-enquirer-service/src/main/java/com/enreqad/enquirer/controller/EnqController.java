package com.enreqad.enquirer.controller;

import com.enreqad.enquirer.controller.payload.ApiResponse;
import com.enreqad.enquirer.controller.payload.EnqUserRequest;
import com.enreqad.enquirer.entity.EnqUser;
import com.enreqad.enquirer.repository.EnqUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enquirer")
public class EnqController {

    @Autowired
    private EnqUserRepository enqUserRepository;

    @PostMapping("/user/create")
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

}
