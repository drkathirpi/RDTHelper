package com.rdthelper.rdthelper.Models;


import lombok.Data;


public class Authorization {

    private String authorization;

    public Authorization(){}
    public Authorization(String authorization){
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return String.format("{ \"Authorization\": \"%s\" }", authorization);
    }
}
