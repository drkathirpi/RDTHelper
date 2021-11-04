package com.rdthelper.rdthelper.Models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String rdtToken;
    private Boolean showAll;


    public User(){}
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.rdtToken = null;
        this.showAll = false;
    }


    public User(String username, String password, String rdtToken, Boolean showAll){
        this.username = username;
        this.password = password;
        this.rdtToken = rdtToken;
        this.showAll = showAll;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password = bCryptPasswordEncoder.encode("test");
    }

    public String getRdtToken() {
        return rdtToken;
    }

    public void setRdtToken(String rdtToken) {
        this.rdtToken = rdtToken;
    }

    public Boolean getShowAll() {
        return showAll;
    }

    public void setShowAll(Boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rdtToken='" + rdtToken + '\'' +
                ", showAll=" + showAll +
                '}';
    }
}
