package com.rdthelper.rdthelper.Models;


import lombok.Data;

@Data
public class RDTFile {

    private Integer id;
    private String path;
    private Long bytes;
    private Integer selected;

    public RDTFile(){}
}
