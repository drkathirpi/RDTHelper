package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();
    User findByUsername(String s);
    User save(User s);
    void update(User s);
}
