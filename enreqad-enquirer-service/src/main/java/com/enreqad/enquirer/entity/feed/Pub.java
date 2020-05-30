package com.enreqad.enquirer.entity.feed;

import com.enreqad.enquirer.entity.Auditable;
import com.enreqad.enquirer.entity.EnqUser;
import com.enreqad.enquirer.entity.topic.BaseTopicEnum;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(schema = "enreqad_enquirer")
public class Pub extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "topic", length = 10)
    @Enumerated(EnumType.STRING)
    private BaseTopicEnum baseTopicEnum;

    @Column(name = "content", nullable = false)
    @Lob()
    private String content;

    @Column(name = "isQuestion", nullable = false)
    private boolean isQuestion;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EnqUser enqUser;

    public Pub(){}

    public Pub(BaseTopicEnum baseTopicEnum, String content, EnqUser enqUser, boolean isQuestion)
    {
        this.baseTopicEnum = baseTopicEnum;
        this.content = content;
        this.enqUser = enqUser;
        this.isQuestion = isQuestion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BaseTopicEnum getBaseTopicEnum() {
        return baseTopicEnum;
    }

    public void setBaseTopicEnum(BaseTopicEnum baseTopicEnum) {
        this.baseTopicEnum = baseTopicEnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    public EnqUser getEnqUser() {
        return enqUser;
    }

    public void setEnqUser(EnqUser enqUser) {
        this.enqUser = enqUser;
    }
}
