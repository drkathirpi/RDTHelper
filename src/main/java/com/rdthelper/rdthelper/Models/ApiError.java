package com.rdthelper.rdthelper.Models;


import lombok.Data;

@Data
public class ApiError {
    private Integer error_code;
    private String error;

    public ApiError() {}

    public ApiError(Integer error_code, String error){
        this.error_code = error_code;
        this.error = error;
    }
}
