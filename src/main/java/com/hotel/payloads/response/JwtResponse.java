package com.hotel.payloads.response;

import java.util.List;

public class JwtResponse {
    private Long id;
    private String token;
    private String username;
    private List<String> roles;

    public JwtResponse() {
    }

    public JwtResponse(Long id, String token, String username, List<String> roles) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
