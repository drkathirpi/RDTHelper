package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Api.TorrentsApi;
import com.rdthelper.rdthelper.Exception.RDTResponseError;
import com.rdthelper.rdthelper.Models.Torrents;
import com.rdthelper.rdthelper.Repositories.TorrentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
public class TorrentsController {

    @Autowired
    TorrentsApi torrentsApi;

    @GetMapping("/")
    public String torrents(Model model){
        ResponseEntity<?> torrents = torrentsApi.getAll();
        model.addAttribute("torrents", torrentsApi.getAll());
        return "torrents";
    }

    @GetMapping("/info/{id}")
    public String torrentInfo(Model model, @PathVariable("id") String id){
        System.out.println(String.format("%s zefinzefinzf", id));
        model.addAttribute("torrent", torrentsApi.getOne(id).getBody());
        return "info";
    }

    @GetMapping("/links/{id}")
    public String torrentLinks(Model model, @PathVariable("links") String id){
        return "info";
    }
}
