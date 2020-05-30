package com.enreqad.enquirer.entity.feed.payload;

import java.util.HashMap;
import java.util.List;

public class PubRes {

    private int persistenceStatus;

    private String content;

    private String topic;

    private HashMap<Long, String> taggedUsers = new HashMap();

    private int visibility;

    private List<Byte[]> images;

    public PubRes(){}

    public PubRes(int persistenceStatus)
    {
        this.persistenceStatus = persistenceStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public HashMap<Long, String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(HashMap<Long, String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public List<Byte[]> getImages() {
        return images;
    }

    public void setImages(List<Byte[]> images) {
        this.images = images;
    }

    public int getPersistenceStatus() {
        return persistenceStatus;
    }

    public void setPersistenceStatus(int persistenceStatus) {
        this.persistenceStatus = persistenceStatus;
    }
}
