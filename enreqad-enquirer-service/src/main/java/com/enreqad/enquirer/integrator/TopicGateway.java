package com.enreqad.enquirer.integrator;

import com.enreqad.enquirer.entity.EnqUser;
import com.enreqad.enquirer.entity.feed.Pub;
import com.enreqad.enquirer.util.GenericResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface TopicGateway {

    public abstract GenericResponse getAllPubs();
    public  abstract GenericResponse getAllPubsForUser(EnqUser user);

}
