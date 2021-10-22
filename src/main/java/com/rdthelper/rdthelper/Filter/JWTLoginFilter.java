package com.rdthelper.rdthelper.Filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Exception.NoValidCredential;
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

        //Web
        if (request.getParameter("username") != null){
            username = request.getParameter("username");
            password = request.getParameter("password");
        }else if (!body.isEmpty()){
            //Api
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                Map<String, Object> map = objectMapper.readValue(body, Map.class);
                username = map.get("username").toString();
                password = map.get("password").toString();
            }catch (JsonParseException e){
                String[] credentials = body.split("&");
                username = credentials[0].split("=")[1];
                password = credentials[1].split("=")[1];

            }

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

        System.out.println("successfulAuthentication");
        String authorizationString = TokenAuthService.generateToken(authResult.getName());

        response.setContentType("application/json");

        //Ecriture du body
        response.getWriter().write(new Authorization(authorizationString).toString());

        //Création du cookie
        Cookie cookie = new Cookie("Authorization", authorizationString);
        cookie.setPath("/web");

        response.addCookie(cookie);
        response.setStatus(200);

        response.sendRedirect("/web/home");


        System.out.println("Authorization=" + authorizationString);
    }
}
