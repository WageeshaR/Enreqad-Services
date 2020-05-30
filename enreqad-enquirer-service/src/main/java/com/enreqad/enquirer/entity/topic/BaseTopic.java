package com.enreqad.enquirer.entity.topic;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(schema = "enreqad_enquirer", name = "topic")
public class BaseTopic {

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "topic_name", length = 10)
    @Enumerated(EnumType.STRING)
    @NaturalId
    private BaseTopicEnum name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BaseTopicEnum getName() {
        return name;
    }

    public void setName(BaseTopicEnum topicName) {
        this.name = topicName;
    }


}
