package com.quodex.JobSpark.jwt;

public class AuthResponse {
    private String token;
    private Long id;
    private String username;
    private String name;
    private String accountType;

    public AuthResponse(String token, Long id,String username, String name, String accountType) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.name = name;
        this.accountType = accountType;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public String getName() {
        return name;
    }

    public String getAccountType() {
        return accountType;
    }
}
