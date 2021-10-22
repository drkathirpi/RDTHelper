package com.rdthelper.rdthelper.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

public class TokenAuthService {

    private static final long EXPIRATION = 864_000_000;
    private static final String SECRET = "TODOCacherLaSecretPassPhrase";
    private static final String PREFIX = "Bearer ";
    private final static String HEADER = "Authorization";

    public static void addAuth(HttpServletResponse res, String username){
        String JWT = generateToken(username);
        res.addHeader(HEADER, PREFIX + " " + JWT);
    }


    public static String generateToken(String username){
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    private static Authentication parseToken(String token){
        if (token.contains(PREFIX)){
            token = token.replace(PREFIX, "");
        }
        String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody()
                .getSubject();
        return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {

        String tokenHeader = request.getHeader(HEADER);
        Cookie[] cookies = request.getCookies();


        if (tokenHeader != null) {
            return parseToken(tokenHeader);
        }

        if (cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(HEADER)){
                    return parseToken(cookie.getValue());
                }
            }
        }
        return null;
    }
}
