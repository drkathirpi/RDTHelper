package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.User;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface IUserService {

    List<User> findAll();
    User findByUsername(String s);
    User save(User s);
}
