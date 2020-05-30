package com.enreqad.enquirer.integrator;

import com.enreqad.enquirer.entity.EnqUser;
import com.enreqad.enquirer.entity.feed.Pub;
import com.enreqad.enquirer.entity.topic.BaseTopic;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import com.enreqad.enquirer.repository.BaseTopicRepository;
import com.enreqad.enquirer.repository.PubImageRepository;
import com.enreqad.enquirer.repository.PubRepository;
import com.enreqad.enquirer.util.GenericResponse;
import com.enreqad.enquirer.util.GlobalConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service
public class TopicGatewayImpl implements TopicGateway {

    @Autowired
    PubRepository pubRepository;

    @Autowired
    PubImageRepository pubImageRepository;

    @Autowired
    BaseTopicRepository baseTopicRepository;

    @Autowired
    AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    public GenericResponse<Collection<Pub>> getAllPubs() {

        GenericResponse<Collection<Pub>> genericResponse = new GenericResponse();
        genericResponse.setResponseMessage(GlobalConstants.SUCCESS_MESSAGE);
        genericResponse.setResponseStatus(GlobalConstants.SUCCESS);
        try {
            List<Pub> pubList = pubRepository.findAll(Sort.by(Sort.Direction.DESC, "updated_date"));
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

    @Override
    public GenericResponse<Collection<Pub>> getAllPubsForUser(EnqUser user) {

        GenericResponse<Collection<Pub>> genericResponse = new GenericResponse();
        genericResponse.setResponseMessage(GlobalConstants.SUCCESS_MESSAGE);
        genericResponse.setResponseStatus(GlobalConstants.SUCCESS);
        List<Pub> fullPubList = new ArrayList();
        Set<BaseTopic> userTopics = user.getBaseTopics();
        Iterator iterator = userTopics.iterator();
        while (iterator.hasNext())
        {
            BaseTopic topic = (BaseTopic) iterator.next();
            Object gatewayClassObj = null;
            try {
                gatewayClassObj = getGatewayClassByName(topic.getName()).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                genericResponse.setResponseMessage(GlobalConstants.FAILED_MESSAGE + ": " + e);
                genericResponse.setResponseStatus(GlobalConstants.FAILED);
                continue;
            }
            if (gatewayClassObj != null && gatewayClassObj instanceof TopicGateway)
            {
                TopicGateway gateway = (TopicGateway) gatewayClassObj;
                autowireCapableBeanFactory.autowireBean(gateway);
                GenericResponse response = gateway.getAllPubs();
                if ( response != null && ( response.getData() instanceof Collection ) && !((Collection) response.getData()).isEmpty() ) fullPubList.addAll( (Collection<Pub>) response.getData());
            }
        }
        if (!fullPubList.isEmpty())
        {
            genericResponse.setData(fullPubList);
        }
        return genericResponse;
    }

    private Class<?> getGatewayClassByName(BaseTopicEnum topicEnum)
    {
        String integratorPackageName = StringUtils.lowerCase(topicEnum.name());
        String gatewayClassName = integratorPackageName.substring(0,1).toUpperCase() + integratorPackageName.substring(1) + "Gateway";
        try {
            String classNameWithPackage = this.getClass().getPackage().getName() + "." + integratorPackageName + "." + gatewayClassName;
            return Class.forName(classNameWithPackage);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return this.getClass();
        }
    }
}
