package com.rdthelper.rdthelper.Api;

import com.rdthelper.rdthelper.Models.Torrents;
import com.rdthelper.rdthelper.Repositories.TorrentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TorrentsApi {

    @Autowired
    TorrentsRepository torrentsRepository;

    @GetMapping("/api/torrents")
    public Torrents[] getAll(){
        return torrentsRepository.getAll();
    }

    @GetMapping("/api/torrents/{id}")
    public Torrents getOne(@PathVariable(value = "id") String id){
        return torrentsRepository.getOne(id);
    }

    @GetMapping("/api/torrents/accept/{id}")
    public void acceptAll(@PathVariable(value = "id") String id){ torrentsRepository.acceptAllFile(id); }


}
