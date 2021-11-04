package com.rdthelper.rdthelper.Config;

import com.rdthelper.rdthelper.Models.Authorization;
import com.rdthelper.rdthelper.Service.TokenAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefererAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final static String JSON = "application/json";
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private String defaultSuccessURL;

    public RefererAuthenticationSuccessHandler(){
        super();
        setUseReferer(true);
    }


    public RefererAuthenticationSuccessHandler(String defaultSuccessURL){
        super();
        setUseReferer(true);
        this.defaultSuccessURL = defaultSuccessURL;
    }



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("successfulAuthentication");
        String authorizationString = TokenAuthService.generateToken(authentication.getName());

        response.setContentType(JSON);

        //Ecriture du body
        response.getWriter().write(new Authorization(authorizationString).toString());

        //Création du cookie
        Cookie cookie = new Cookie("Authorization", authorizationString);
        cookie.setPath("/web");

        response.addCookie(cookie);
        response.setStatus(200);

        System.out.println("Authorization=" + authorizationString);

        if (request.getRequestURI().contains("/web/")){
            handle(request, response, authentication);
        }
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, defaultSuccessURL);
    }
}