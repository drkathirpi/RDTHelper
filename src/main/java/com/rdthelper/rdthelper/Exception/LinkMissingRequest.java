package com.rdthelper.rdthelper.Exception;

public class LinkMissingRequest extends Exception{

    private String message;

    public LinkMissingRequest(){}
    LinkMissingRequest(String message){
        this.message = message;
    }
}
