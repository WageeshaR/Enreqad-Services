package com.enreqad.enquirer.integrator.com_it;

import com.enreqad.enquirer.entity.feed.Pub;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import com.enreqad.enquirer.integrator.TopicGateway;
import com.enreqad.enquirer.integrator.TopicGatewayImpl;
import com.enreqad.enquirer.repository.PubRepository;
import com.enreqad.enquirer.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Component
@Service
public class Com_itGateway extends TopicGatewayImpl {

    private BaseTopicEnum topicName = BaseTopicEnum.COM_IT;
    @Autowired
    PubRepository pubRepository;

    @Override
    public GenericResponse<Collection<Pub>> getAllPubs()
    {

        return null;
    }
}
