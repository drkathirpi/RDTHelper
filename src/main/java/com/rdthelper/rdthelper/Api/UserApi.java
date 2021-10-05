package com.rdthelper.rdthelper.Api;


import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Service.IUserService;
import com.rdthelper.rdthelper.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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
    public ResponseEntity<?> save(@RequestBody User user){
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
}
