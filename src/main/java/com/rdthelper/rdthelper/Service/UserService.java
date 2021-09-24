package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Repositories.SettingsRepository;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SettingsRepository settingsRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User s) {
        settingsRepository.save(s.getSettings());
        return userRepository.save(s);
    }
}
