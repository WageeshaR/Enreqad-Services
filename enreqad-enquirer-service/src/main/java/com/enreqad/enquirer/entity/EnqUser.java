package com.enreqad.enquirer.entity;

import com.enreqad.enquirer.entity.topic.BaseTopic;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(schema = "enreqad_enquirer", name = "enq_user")
public class EnqUser {

    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_topics",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "topic_id")
    )
    private Set<BaseTopic> baseTopics = new HashSet();

    public EnqUser(){}

    public EnqUser( long userId, String username, String fullName )
    {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
    }

    public EnqUser(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public Set<BaseTopic> getBaseTopics() {
        return baseTopics;
    }

    public void setBaseTopics(Set<BaseTopic> baseTopics) {
        this.baseTopics = baseTopics;
    }
}
