package com.rdthelper.rdthelper.Config;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class RefererAuthentificationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public RefererAuthentificationSuccessHandler() {
        super();
        setUseReferer(true);
    }

}