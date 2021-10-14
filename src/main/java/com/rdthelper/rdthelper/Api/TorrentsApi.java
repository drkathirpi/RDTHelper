package com.rdthelper.rdthelper.Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Exception.LinkMissingRequest;
import com.rdthelper.rdthelper.Models.ApiError;
import com.rdthelper.rdthelper.Models.LinkRequest;
import com.rdthelper.rdthelper.Models.RDTUpload;
import com.rdthelper.rdthelper.Service.TorrentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin
@RestController
public class TorrentsApi {

    @Autowired
    TorrentsService torrentsService;

    @GetMapping("/torrents")
    public ResponseEntity<?> getAll(){
        return torrentsService.getAll();
    }

    @GetMapping("/torrents/{id}")
    public ResponseEntity<?> getOne(@PathVariable(value = "id") String id){
        return torrentsService.getOne(id);
    }

    @DeleteMapping("/torrents/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable(value = "id") String id){
        return torrentsService.deleteOne(id);
    }

    @GetMapping("/torrents/accept/{id}")
    public ResponseEntity<?> startTorrent(@PathVariable(value = "id") String id){
        return torrentsService.startTorrent(id);
    }

    @PostMapping("/torrent/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile... files) throws IOException {
        return torrentsService.addTorrent(files);
    }

    @PostMapping("/torrents/debrid")
    public ResponseEntity<?> debridLink(@RequestBody LinkRequest link) throws LinkMissingRequest {
        return torrentsService.debridLink(link);
    }
}
