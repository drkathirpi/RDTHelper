package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Api.TorrentsApi;
import com.rdthelper.rdthelper.Exception.NoUserFoundExcpetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/web")
@Controller()
public class TorrentsController {

    @Autowired
    TorrentsApi torrentsApi;

    @GetMapping("/torrents")
    public String torrents(Model model){
        return "torrents";
    }

    @GetMapping("/torrents/info/{id}")
    public String torrentInfo(Model model, @PathVariable("id") String id) throws NoUserFoundExcpetion {
        model.addAttribute("torrent", torrentsApi.getOne(id).getBody());
        return "info";
    }

    @GetMapping("/torrents/links/{id}")
    public String torrentLinks(Model model, @PathVariable("links") String id){
        return "info";
    }

}
