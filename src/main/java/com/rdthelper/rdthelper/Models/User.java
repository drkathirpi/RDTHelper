package com.rdthelper.rdthelper.Models;


import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String username;
    private String password;

    private String rdtToken;
    private Boolean showAll;


    public User(){}


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
        this.password = bCryptPasswordEncoder.encode(password);
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
