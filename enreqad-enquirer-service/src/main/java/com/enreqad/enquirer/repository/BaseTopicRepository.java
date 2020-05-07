package com.enreqad.enquirer.repository;

import com.enreqad.enquirer.entity.topic.BaseTopic;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseTopicRepository extends JpaRepository<BaseTopic, Long>
{
    Optional<BaseTopic> findByName(BaseTopicEnum topic);
}
