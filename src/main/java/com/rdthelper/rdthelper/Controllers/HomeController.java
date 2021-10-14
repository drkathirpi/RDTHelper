package com.rdthelper.rdthelper.Controllers;


import com.rdthelper.rdthelper.Exception.LinkMissingRequest;
import com.rdthelper.rdthelper.Models.DebridArray;
import com.rdthelper.rdthelper.Models.LinkRequest;
import com.rdthelper.rdthelper.Models.Torrents;
import com.rdthelper.rdthelper.Service.TorrentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private TorrentsService torrentsService;


    @GetMapping("/")
    public String home(){
        return "home";
    }


    @PostMapping("/")
    public String postHome(@RequestParam("ids") String[] links, Model model) throws LinkMissingRequest {
        StringBuilder nonDebridLinks = new StringBuilder();
        for (String link : links){
            Torrents torrent = (Torrents) torrentsService.getOne(link).getBody();
            String[] allLinks =  torrent != null ? torrent.getLinks() : new String[0];

            for (String l : allLinks){
                nonDebridLinks.append(l).append("\n");
            }
        }
        model.addAttribute("links", nonDebridLinks);
        return "home";
    }
}
