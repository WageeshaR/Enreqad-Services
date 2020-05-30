package com.enreqad.enquirer.integrator.auto;

import com.enreqad.enquirer.entity.feed.Pub;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import com.enreqad.enquirer.integrator.TopicGateway;
import com.enreqad.enquirer.integrator.TopicGatewayImpl;
import com.enreqad.enquirer.repository.PubRepository;
import com.enreqad.enquirer.util.GenericResponse;
import com.enreqad.enquirer.util.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Component
@Service
public class AutoGateway extends TopicGatewayImpl {

    private BaseTopicEnum topicName = BaseTopicEnum.AUTO;

    @Autowired
    PubRepository pubRepository;

    @Override
    public GenericResponse<Collection<Pub>> getAllPubs()
    {
        GenericResponse<Collection<Pub>> genericResponse = new GenericResponse();
        genericResponse.setResponseMessage(GlobalConstants.SUCCESS_MESSAGE);
        genericResponse.setResponseStatus(GlobalConstants.SUCCESS);
        try {
            List<Pub> pubList = pubRepository.findByBaseTopicEnum(topicName).orElseThrow( () -> new Exception("No pubs found for the topic: " + topicName.name()) );
            if (pubList.isEmpty())
            {
                return genericResponse;
            }
            genericResponse.setData(pubList);
        } catch (Exception e)
        {
            e.printStackTrace();
            genericResponse.setResponseMessage(GlobalConstants.FAILED_MESSAGE + ": " + e);
            genericResponse.setResponseStatus(GlobalConstants.FAILED);
        }
        return genericResponse;
    }
}
