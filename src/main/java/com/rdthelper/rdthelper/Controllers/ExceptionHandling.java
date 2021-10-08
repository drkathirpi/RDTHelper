package com.rdthelper.rdthelper.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
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
}
