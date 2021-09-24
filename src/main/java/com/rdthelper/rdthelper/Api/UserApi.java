package com.rdthelper.rdthelper.Api;


import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Service.IUserService;
import com.rdthelper.rdthelper.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApi {

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/users")
    public User save(@RequestBody User user){
        return userService.save(user);
    }

}
