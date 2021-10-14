package com.rdthelper.rdthelper.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomHandlerInterceptorAdapter implements AsyncHandlerInterceptor {

    private final static Logger logger = Logger.getLogger(CustomHandlerInterceptorAdapter.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.log(Level.INFO, String.format("[%s] Request: %s :: USER: %s (%s)", request.getMethod(), request.getRequestURL().toString(), SecurityContextHolder.getContext().getAuthentication().getName(), request.getRemoteAddr()));


        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }
}
