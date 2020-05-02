package com.enreqad.enquirer.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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
}
