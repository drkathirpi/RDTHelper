package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.CustomUserDetails;
import com.rdthelper.rdthelper.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);
        if (user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new CustomUserDetails(user);
    }
}
