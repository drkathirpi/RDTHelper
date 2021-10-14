package com.rdthelper.rdthelper;


import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserJpaTest {

    @Autowired
    private UserRepository userRepository;


    private static User generateUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRdtToken("test");
        return user;
    }

    @Test
    public void shouldCreateNewUser(){
        User result = userRepository.save(generateUser());
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldFindUser(){
        User user = generateUser();
        userRepository.save(user);
        assertThat(userRepository.findByUsername("test")).isNotNull();
    }

    @Test
    public void shouldUpdateUser(){
        User user = generateUser();
        userRepository.save(user);
        user.setUsername("test1");
        userRepository.save(user);
        assertThat(userRepository.findByUsername("test1")).isNotNull();
    }

    @Test
    public void shouldDeleteUser(){
        User user = generateUser();
        userRepository.save(user);
        userRepository.delete(user);
        assertThat(userRepository.findByUsername("test")).isNull();
    }
}
