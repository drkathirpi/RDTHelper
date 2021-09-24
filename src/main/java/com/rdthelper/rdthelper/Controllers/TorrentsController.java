package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Api.TorrentsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
public class TorrentsController {

    @Autowired
    TorrentsApi torrentsApi;

    @GetMapping("/torrents")
    public String torrents(Model model){
        ResponseEntity<?> torrents = torrentsApi.getAll();
        model.addAttribute("torrents", torrentsApi.getAll());
        return "torrents";
    }

    @GetMapping("/torrents/info/{id}")
    public String torrentInfo(Model model, @PathVariable("id") String id){
        model.addAttribute("torrent", torrentsApi.getOne(id).getBody());
        return "info";
    }

    @GetMapping("/torrents/links/{id}")
    public String torrentLinks(Model model, @PathVariable("links") String id){
        return "info";
    }
}
