package com.enreqad.enquirer.repository;

import com.enreqad.enquirer.entity.EnqUser;
import com.enreqad.enquirer.entity.feed.Pub;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PubRepository extends JpaRepository<Pub, Long> {
    Optional<List<Pub>> findByEnqUser(EnqUser u);

    Optional<List<Pub>> findByBaseTopicEnum(BaseTopicEnum e);
}
