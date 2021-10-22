package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import com.rdthelper.rdthelper.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("/web")
@Controller
public class LoginController {


    @Autowired
    private UserService userService;


    private Boolean hasUser(){
        Long nbrUser = userService.count();
        return !nbrUser.equals(0L);
    }


    @GetMapping("/signup")
    public String signup(Model model){
        if (hasUser()) return "redirect:/web/login";

        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/perform_signup")
    public String performSignup(@ModelAttribute User user, Model model){
        userService.save(user);
        return "redirect:/web/login";
    }

    @GetMapping("/login")
    public String Login(){
        if (!hasUser()) {
            Logger.getLogger(LoginController.class.getName()).log(Level.INFO, "No user found redirect to signup");
            return "redirect:/web/signup";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/web/login?logout";
    }

}
