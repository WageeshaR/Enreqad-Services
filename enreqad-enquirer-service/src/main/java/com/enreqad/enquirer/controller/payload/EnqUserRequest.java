package com.enreqad.enquirer.controller.payload;

import javax.validation.constraints.NotBlank;

public class EnqUserRequest {

    @NotBlank
    private long userId;

    @NotBlank
    private String username;

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
}
