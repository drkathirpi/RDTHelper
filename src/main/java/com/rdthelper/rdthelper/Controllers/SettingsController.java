package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import com.rdthelper.rdthelper.Service.IUserService;
import com.rdthelper.rdthelper.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {

    @Autowired
    private UserService userRepository;


    @GetMapping("/settings")
    public String getSettings(Model model) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user);
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/perform_settings")
    public String performSettings(@ModelAttribute User user, Model model){
        userRepository.update(user);
        return "settings";
    }

}
