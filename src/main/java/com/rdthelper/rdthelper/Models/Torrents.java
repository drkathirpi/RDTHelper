package com.rdthelper.rdthelper.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Torrents {

    private String id;
    private String filename;
    private String hash;
    private Long bytes;
    private String host;
    private Integer split;
    private Integer progress;
    private String downloaded;
    private String added;
    private RDTFile[] files;
    private String[] links;
    private String ended;
    private Integer speed;
    private Integer seeders;

    Torrents(){}
}
