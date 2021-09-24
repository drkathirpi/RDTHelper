package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();
    User save(User s);
}
