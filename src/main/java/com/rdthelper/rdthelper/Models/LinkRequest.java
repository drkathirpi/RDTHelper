package com.rdthelper.rdthelper.Models;

import lombok.Data;

@Data
public class LinkRequest {
    private String link;

    public LinkRequest(){}

    public LinkRequest(String link) {
        this.link = link;
    }

    @Override
    public String toString(){
        return link + "\n";
    }
}
