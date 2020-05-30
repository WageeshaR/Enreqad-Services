package com.enreqad.enquirer.entity.feed.payload;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class PubReq {

    @NotBlank
    private String topic;

    @NotBlank
    private String content;

    private List<Byte[]> images = new ArrayList();

    private List<Long> taggedUserIds = new ArrayList();

    @NotBlank
    private int visibility;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Byte[]> getImages() {
        return images;
    }

    public void setImages(List<Byte[]> images) {
        this.images = images;
    }

    public List<Long> getTaggedUserIds() {
        return taggedUserIds;
    }

    public void setTaggedUserIds(List<Long> taggedUserIds) {
        this.taggedUserIds = taggedUserIds;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

}
