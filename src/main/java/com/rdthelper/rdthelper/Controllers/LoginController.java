package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.util.List;

@Controller
public class LoginController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signin")
    public String signin(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return  "signin";
    }

    @PostMapping("/perform_signin")
    public String persormSignin(@ModelAttribute User user, Model model){
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String Login(){
        Long nbrUser = userRepository.count();
        if (nbrUser.equals(0L)){
            return "redirect:/signin";
        }

        List<User> users = userRepository.findAll();
        System.out.println(users);
        return "login";
    }

}
