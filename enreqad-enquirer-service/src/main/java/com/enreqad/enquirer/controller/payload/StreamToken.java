package com.enreqad.enquirer.controller.payload;

import io.getstream.core.http.Token;

public class StreamToken {
    private String token;

    public StreamToken(Token token)
    {
        this.token = token.toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
