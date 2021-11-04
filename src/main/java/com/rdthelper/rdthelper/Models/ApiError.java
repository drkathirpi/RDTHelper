package com.rdthelper.rdthelper.Models;


import lombok.Data;

@Data
public class ApiError {
    private Integer error_code;
    private String error;
    private String link =  "Please visit for help https://api.real-debrid.com/#api_error_codes";

    public ApiError() {}

    public ApiError(Integer error_code, String error){
        this.error_code = error_code;
        this.error = error;
    }

    public ApiError(Integer error_code, String error, String link){
        this.error_code = error_code;
        this.error = error;
        this.link = link;
    }
}
