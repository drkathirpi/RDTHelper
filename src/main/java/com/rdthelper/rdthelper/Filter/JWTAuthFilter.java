package com.rdthelper.rdthelper.Filter;

import com.rdthelper.rdthelper.Exception.TokenExpiredException;
import com.rdthelper.rdthelper.Service.TokenAuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthFilter extends GenericFilterBean {

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try{
            Authentication authentication = TokenAuthService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (ExpiredJwtException e){
            request.setAttribute("tokenStatus", "expired");
        }catch (SignatureException e){
            request.setAttribute("tokenStatus", "signature");
        }


        chain.doFilter(request, response);
    }

}
