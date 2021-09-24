package com.rdthelper.rdthelper.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class RDTResponseError extends HttpClientErrorException {

    public RDTResponseError(HttpStatus httpStatus){
        super(httpStatus);
    }
    public RDTResponseError(HttpStatus httpStatus, String message){
        super(httpStatus, message);
    }
}
