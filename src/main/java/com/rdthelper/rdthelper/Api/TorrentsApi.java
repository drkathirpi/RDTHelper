package com.rdthelper.rdthelper.Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rdthelper.rdthelper.Exception.RDTResponseError;
import com.rdthelper.rdthelper.Models.ApiError;
import com.rdthelper.rdthelper.Models.Torrents;
import com.rdthelper.rdthelper.Repositories.TorrentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api")
@CrossOrigin
@RestController
public class TorrentsApi {

    @Autowired
    TorrentsRepository torrentsRepository;

    @GetMapping("/torrents")
    public ResponseEntity<?> getAll(){
        try {
            return torrentsRepository.getAll();
        }catch (JsonProcessingException e){
            return new ResponseEntity<>(new ApiError(500, "Cannot parse JSON"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/torrents/{id}")
    public ResponseEntity<?> getOne(@PathVariable(value = "id") String id){
        try{
            return torrentsRepository.getOne(id);
        }catch (JsonProcessingException e){
            return new ResponseEntity<>(new Torrents(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/torrents/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable(value = "id") String id){
        try {
            return torrentsRepository.deleteOne(id);
        }catch (JsonProcessingException e){
            return new ResponseEntity<>(new ApiError(500, "Cannot parse JSON"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/torrents/accept/{id}")
    public void acceptAll(@PathVariable(value = "id") String id){ torrentsRepository.acceptAllFile(id); }


}
