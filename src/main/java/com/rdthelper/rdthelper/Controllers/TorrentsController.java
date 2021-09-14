package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Api.TorrentsApi;
import com.rdthelper.rdthelper.Repositories.TorrentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TorrentsController {

    @Autowired
    TorrentsApi torrentsApi;

    @GetMapping("/torrents")
    public String torrents(Model model){
        model.addAttribute("torrents", torrentsApi.getAll());
        return "torrents";
    }
}
