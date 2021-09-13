package com.rdthelper.rdthelper.Controllers;

import com.rdthelper.rdthelper.Repositories.TorrentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TorrentsController {

    @Autowired
    TorrentsRepository torrentsRepository;

    @GetMapping("/torrents")
    public String torrents(Model model){
        model.addAttribute("torrents", torrentsRepository.getAll());
        return "torrents";
    }
}
