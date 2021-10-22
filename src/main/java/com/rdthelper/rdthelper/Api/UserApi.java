package com.rdthelper.rdthelper.Api;


import com.rdthelper.rdthelper.Exception.UserAlreadyCreatedException;
import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Service.IUserService;
import com.rdthelper.rdthelper.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<?> save(@RequestBody User user){
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) throws UserAlreadyCreatedException {
        if (!userService.findAll().isEmpty()){
            throw new UserAlreadyCreatedException("Only one user can be created");
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
}
