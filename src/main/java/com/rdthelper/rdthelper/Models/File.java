package com.rdthelper.rdthelper.Models;

import lombok.Data;

@Data
public class File {

    private String name;
    private String url;

    public File(){}
    public File(String name, String url){
        this.name = name;
        this.url = url;
    }
}
