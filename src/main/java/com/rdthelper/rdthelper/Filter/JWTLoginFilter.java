package com.rdthelper.rdthelper.Filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Models.Authorization;
import com.rdthelper.rdthelper.Service.TokenAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("attemptAuthentication");
        String username;
        String password;
        String body = request.getReader().lines().collect(Collectors.joining());
        if (request.getParameter("username") != null){
            username = request.getParameter("username");
            password = request.getParameter("password");
        }else if (!body.isEmpty()){
            System.out.printf("voici le body: %s%n", body);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(body, Map.class);
            username = map.get("username").toString();
            password = map.get("password").toString();
        }else {
            username = null;
            password = null;
        }

        System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s", username, password);
        System.out.println();

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        System.out.println("JWTLoginFilter.successfulAuthentication:");

        String authorizationString = TokenAuthService.generateToken(authResult.getName());

        response.setContentType("application/json");

        response.getWriter().write(new Authorization(authorizationString).toString());
        Cookie cookie = new Cookie("Authorization", authorizationString);
        cookie.setPath("/web");
        response.addCookie(cookie);
        System.out.println("Authorization String=" + authorizationString);
    }
}
