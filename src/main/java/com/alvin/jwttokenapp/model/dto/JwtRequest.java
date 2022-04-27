package com.alvin.jwttokenapp.model.dto;

import java.io.Serial;
import java.io.Serializable;

public class JwtRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    public JwtRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}