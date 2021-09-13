package com.rdthelper.rdthelper.Models;


import lombok.Data;

@Data
public class Torrents {

    private String id;
    private String filename;
    private String hash;
    private Long bytes;
    private String host;
    private Integer splt;
    private Integer progress;
    private String downloaded;
    private String added;
    private String[] links;
    private String ended;
    private Integer speed;
    private Integer seeders;

    Torrents(){}
}
