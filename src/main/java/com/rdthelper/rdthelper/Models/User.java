package com.rdthelper.rdthelper.Models;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String username;
    private String password;

    @OneToOne
    private Settings settings;


    public User(){}


}
