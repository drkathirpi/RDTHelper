package com.rdthelper.rdthelper.Filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Config.RefererAuthenticationSuccessHandler;
import com.rdthelper.rdthelper.Exception.NoValidCredential;
import com.rdthelper.rdthelper.Models.Authorization;
import com.rdthelper.rdthelper.Models.User;
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
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        RefererAuthenticationSuccessHandler refererAuthenticationSuccessHandler = new RefererAuthenticationSuccessHandler();
        refererAuthenticationSuccessHandler.onAuthenticationSuccess(req, res, auth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("attemptAuthentication");
        User user = new User();
        String body = request.getReader().lines().collect(Collectors.joining());

        //Web
        if (request.getParameter("username") != null){
            user = new User(request.getParameter("username"),
                    request.getParameter("password"),
                    null,
                    false);
        }else if (!body.isEmpty()){
            //Api
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                Map<String, Object> map = objectMapper.readValue(body, Map.class);
                user = new User(map.get("username").toString(),
                        map.get("password").toString());
            }catch (JsonParseException | NullPointerException e){
                String[] credentials = body.split("&");
                if (credentials.length > 2){
                    user = new User(credentials[0].split("=")[1],
                            credentials[1].split("=")[1]);

                }
            }
        }

        System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s", user.getUsername(), user.getPassword());
        System.out.println();

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList()));
    }


}
