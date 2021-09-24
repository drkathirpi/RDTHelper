package com.rdthelper.rdthelper.Models;


import lombok.Data;

@Data
public class Link {

    private String id;
    private String filename;
    private String mimeType;
    private Long filesize;
    private String link;
    private String host;
    private Integer chunks;
    private Integer crc;
    private String download;
    private Boolean streamable;

    public Link(String id, String filename, String mimeType, Long filesize, String link, String host, Integer chunks, Integer crc, String download, Boolean streamable) {
        this.id = id;
        this.filename = filename;
        this.mimeType = mimeType;
        this.filesize = filesize;
        this.link = link;
        this.host = host;
        this.chunks = chunks;
        this.crc = crc;
        this.download = download;
        this.streamable = streamable;
    }

    public Link() {
    }
}
