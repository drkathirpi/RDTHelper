package com.rdthelper.rdthelper.Config;

import ch.qos.logback.core.net.ObjectWriter;
import ch.qos.logback.core.net.ObjectWriterFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Models.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {



        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String tokenStatus = new String();
        if (request.getAttribute("tokenStatus") != null){
            tokenStatus = (String) request.getAttribute("tokenStatus");

        }

        if (tokenStatus.equals("expired")){
            response.getWriter().write(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(new ApiError(403,"Token expired")));
        }else if (tokenStatus.equals("signature")){
            response.getWriter().write(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(new ApiError(403,"Token malformed")));
        } else {
            response.getWriter().write(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(new ApiError(403,"Unauthorized", "Not Valid token found")));
        }

    }
}
