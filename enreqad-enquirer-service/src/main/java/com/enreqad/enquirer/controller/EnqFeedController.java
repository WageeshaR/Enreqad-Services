package com.enreqad.enquirer.controller;

import com.enreqad.enquirer.controller.payload.ApiResponse;
import com.enreqad.enquirer.controller.payload.StreamToken;
import com.enreqad.enquirer.entity.EnqUser;
import com.enreqad.enquirer.entity.feed.Pub;
import com.enreqad.enquirer.entity.feed.PubImage;
import com.enreqad.enquirer.entity.feed.payload.PubReq;
import com.enreqad.enquirer.entity.feed.payload.PubRes;
import com.enreqad.enquirer.entity.topic.BaseTopic;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import com.enreqad.enquirer.integrator.TopicGateway;
import com.enreqad.enquirer.integrator.TopicGatewayImpl;
import com.enreqad.enquirer.repository.BaseTopicRepository;
import com.enreqad.enquirer.repository.EnqUserRepository;
import com.enreqad.enquirer.repository.PubImageRepository;
import com.enreqad.enquirer.repository.PubRepository;
import com.enreqad.enquirer.util.GenericResponse;
import com.enreqad.enquirer.util.GlobalConstants;
import io.getstream.core.http.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.getstream.client.Client;

import java.net.MalformedURLException;
import java.util.*;

@RestController
@RequestMapping("feed")
public class EnqFeedController {

    @Autowired
    EnqUserRepository enqUserRepository;

    @Autowired
    PubImageRepository pubImageRepository;

    @Autowired
    PubRepository pubRepository;

    @Autowired
    BaseTopicRepository baseTopicRepository;

    @Autowired
    TopicGatewayImpl topicGatewayImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createPub(@RequestHeader("user-id") long userId, @RequestParam boolean question, @RequestBody PubReq pubReq)
    {
        boolean imagesSaved = false;
        int idx = 0;
        try {

            EnqUser user = enqUserRepository.findById(userId).orElseThrow( () -> new Exception("User not found") );
            Pub pub = new Pub(
                    BaseTopicEnum.valueOf(pubReq.getTopic()),
                    pubReq.getContent(),
                    user,
                    question
            );
            pubRepository.save(pub);
            for ( Byte[] image : pubReq.getImages() )
            {
                PubImage pubImage = new PubImage();
                pubImage.setImageData(image);
                pubImage.setImageNo(idx++);
                pubImage.setPub(pub);
                try {
                    pubImageRepository.save(pubImage);
                    imagesSaved = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    imagesSaved = false;
                    break;
                }
            }

            PubRes pubRes = new PubRes();
            pubRes.setContent(pubReq.getContent());
            pubRes.setPersistenceStatus( imagesSaved ? GlobalConstants.ENQ_POST_PERSIST_SUCCESS : GlobalConstants.ENQ_POST_PERSIST_FAILED_IMAGES );
            if ( question )
            {
                for ( Long taggedUId : pubReq.getTaggedUserIds() )
                {
                    pubRes.getTaggedUsers().put(taggedUId, enqUserRepository.findById(taggedUId).get().getUsername());
                }
            }
            pubRes.setTopic(pubReq.getTopic());
            pubRes.setVisibility(pubReq.getVisibility());

            return new ResponseEntity(pubRes, HttpStatus.OK);

        } catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity(new PubRes(GlobalConstants.ENQ_POST_PERSIST_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/feed")
    public ResponseEntity<?> getAllPubs(@RequestHeader("user-id") long userId)
    {
        /**
         * Implement pubs retrieval, automatically filtered by source-user-subscribed topics
         */
        try {
            EnqUser srcUser = enqUserRepository.findById(userId).orElseThrow( () -> new Exception("User not found for user-id: " + userId) );
            GenericResponse<Collection<Pub>> pubs = topicGatewayImpl.getAllPubsForUser(srcUser);
            if (!pubs.getData().isEmpty())
            {
                return new ResponseEntity(pubs.getData(), HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(true, "No pubs found"), HttpStatus.ACCEPTED);
        } catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error fetching data"), HttpStatus.BAD_GATEWAY);
        }
    }



    @GetMapping("/feed/filter")
    public ResponseEntity<?> getPubsByUser(@RequestHeader("user-id") long userId, @RequestParam long targetUserId)
    {
        /**
         * Implements posts retrieval of targetUser filtered by topics subscribed by requested user
         * If no topics overlap between two users, return empty response
         */
        try
        {
            EnqUser srcUser = enqUserRepository.findById(userId).get();
            EnqUser targetUser = enqUserRepository.findById(targetUserId).orElseThrow( () -> new Exception("Target user not found for user-id: " + targetUserId) );
            if (userId == targetUserId)
            {
                List<Pub> pubList = pubRepository.findByEnqUser( srcUser ).orElseThrow( () -> new Exception("Pubs not found for the user-id: " + userId) );
                return new ResponseEntity(pubList, HttpStatus.OK);
            } else
            {
                Set<BaseTopic> srcUserTopics = srcUser.getBaseTopics();
                if ( !srcUserTopics.isEmpty() )
                {
                    List<Pub> pubList = pubRepository.findByEnqUser( targetUser ).orElseThrow( () -> new Exception("Pubs not found for the user-id: " + targetUserId) );
                    Iterator<Pub> pubIterator = pubList.iterator();
                    List<Pub> returnPubList = new ArrayList();
                    while (pubIterator.hasNext())
                    {
                        Pub currentPub = pubIterator.next();
                        BaseTopic currentPubTopic;
                        if (currentPub.getBaseTopicEnum() != null )
                        {
                            currentPubTopic = baseTopicRepository.findByName(currentPub.getBaseTopicEnum()).get();
                            if (srcUserTopics.contains( currentPubTopic )) returnPubList.add(currentPub);
                        }
                    }
                    if ( returnPubList.isEmpty() ) return new ResponseEntity(new ApiResponse(true, "No publications found in target user: " + targetUserId + " matching the subscription criteria of source user: " + userId), HttpStatus.ACCEPTED);
                    else return new ResponseEntity( returnPubList, HttpStatus.OK );
                } else
                {
                    return new ResponseEntity(new ApiResponse(true, "No topic subscriptions found for current user: " + userId), HttpStatus.ACCEPTED);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error fetching pubs data for source user: " + userId + " and target user: " + targetUserId), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stream-auth")
    public ResponseEntity<?> getStreamAuth(@RequestHeader("user-id") long userId) {
        Token userToken = null;
        EnqUser srcUser = enqUserRepository.findById(userId).get();
        try {
            Client client = Client.builder(GlobalConstants.STREAM_API_KEY, GlobalConstants.STREAM_API_SECRET).build();
            userToken = client.frontendToken(srcUser.getUsername());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (userToken == null) return new ResponseEntity(new ApiResponse(false, "Unable to generate stream token for user: " + userId), HttpStatus.INTERNAL_SERVER_ERROR);
        else return new ResponseEntity(new StreamToken(userToken), HttpStatus.OK);
    }
}
