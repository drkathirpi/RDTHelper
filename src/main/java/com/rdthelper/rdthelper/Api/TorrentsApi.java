package com.rdthelper.rdthelper.Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Models.ApiError;
import com.rdthelper.rdthelper.Models.LinkRequest;
import com.rdthelper.rdthelper.Models.RDTUpload;
import com.rdthelper.rdthelper.Service.TorrentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        try {
            return torrentsService.getAll();
        }catch (HttpServerErrorException e){
            try{
                ObjectMapper mapper = new ObjectMapper();
                return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
            }catch (JsonProcessingException jsonException) {
                return new ResponseEntity<>(new ApiError(200, jsonException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @GetMapping("/torrents/{id}")
    public ResponseEntity<?> getOne(@PathVariable(value = "id") String id){
        try{
            return torrentsService.getOne(id);
        }catch (HttpServerErrorException e){
            try{
                ObjectMapper mapper = new ObjectMapper();
                return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
            }catch (JsonProcessingException jsonException) {
                return new ResponseEntity<>(new ApiError(200, jsonException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping("/torrents/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable(value = "id") String id){
            try {
                return torrentsService.deleteOne(id);
            }catch (HttpServerErrorException e){
            try{
                ObjectMapper mapper = new ObjectMapper();
                return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
            }catch (JsonProcessingException jsonException) {
                return new ResponseEntity<>(new ApiError(200, jsonException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/torrents/accept/{id}")
    public ResponseEntity<?> startTorrent(@PathVariable(value = "id") String id){
        try{
            return torrentsService.startTorrent(id);
        }catch (HttpServerErrorException e){
            try{
                ObjectMapper mapper = new ObjectMapper();
                return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
            }catch (JsonProcessingException jsonException) {
                return new ResponseEntity<>(new ApiError(200, jsonException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping("/torrent/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile... files){
        List<RDTUpload> rdtFiles = new ArrayList<>();
        try {
            for (MultipartFile file : files){
                rdtFiles.add(torrentsService.addTorrent(file).getBody());
            }

            return new ResponseEntity<>(rdtFiles, HttpStatus.OK);
        }catch (HttpServerErrorException e){
            try {
                ObjectMapper mapper = new ObjectMapper();
                return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
            } catch (JsonProcessingException jsonException) {
                return new ResponseEntity<>(new ApiError(200, jsonException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (IOException e){
            return new ResponseEntity<>(new ApiError(1, "Cannot read file"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/torrents/debrid")
    public ResponseEntity<?> debridLink(@RequestBody LinkRequest link){

        if (link.getLink() == null || link.getLink().isEmpty()){
            return new ResponseEntity<>(new ApiError(-1, "Link is missing"), HttpStatus.BAD_REQUEST);
        }

        try {
            return torrentsService.debridLink(link);
        }catch (HttpServerErrorException e){
            try{
                ObjectMapper mapper = new ObjectMapper();
                return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
            }catch (JsonProcessingException jsonException) {
                return new ResponseEntity<>(new ApiError(200, jsonException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
