package com.rdthelper.rdthelper.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Exception.LinkMissingRequest;
import com.rdthelper.rdthelper.Models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.ServiceUnavailableException;
import javax.net.ssl.SSLException;
import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientError(HttpServletRequest req, HttpClientErrorException ex) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(mapper.readValue(ex.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> jsonMappingException(HttpServletRequest req, JsonMappingException ex){
        return new ResponseEntity<>(new ApiError(200, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LinkMissingRequest.class)
    public ResponseEntity<?> linkMissingRequest(HttpServletRequest req, LinkMissingRequest ex){
        return new ResponseEntity<>(new ApiError(-1, "Link is missing"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView badCredentialException(HttpServletRequest req, BadCredentialsException ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login?error");
        return modelAndView;
    }


    @ExceptionHandler({SSLException.class, ConnectException.class})
    public ResponseEntity<?> sslException(){
        return new ResponseEntity<>(new ApiError(129, "RealDebrid not responding"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<?> serverExeption() {
        return new ResponseEntity<>(new ApiError(130, "Are you still premium?"), HttpStatus.BAD_REQUEST);
    }



}
