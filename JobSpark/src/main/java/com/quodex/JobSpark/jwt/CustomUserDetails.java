package com.quodex.JobSpark.jwt;


import com.quodex.JobSpark.dto.AccountType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private Long id;
    private String userName;
    private String name;
    private String password;
    private AccountType accountType;
    private Collection<?extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String userName, String name,  String password, AccountType accountType, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.authorities = authorities;
    }

    public CustomUserDetails(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
