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

    public Long count(){
        return userRepository.count();
    }

    @Transactional
    @Override
    public User save(User s) {
        Long nbrUSer = userRepository.count();
        if (!nbrUSer.equals(0L)){
            User user = userRepository.findByUsername(s.getUsername());
            if (user != null){
                s.setId(user.getId());
            }
        }
        return userRepository.save(s);
    }


}
