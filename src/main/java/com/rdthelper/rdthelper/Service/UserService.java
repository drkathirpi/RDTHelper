package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String s) {
        return userRepository.findByUsername(s);
    }

    @Override
    public User save(User s) {
        return userRepository.save(s);
    }

    @Transactional
    @Override
    public void update(User s) {
        userRepository.update(s.getUsername(), s.getPassword(), s.getRdtToken());
    }
}
