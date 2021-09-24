package com.rdthelper.rdthelper.Api;

import com.rdthelper.rdthelper.Models.Settings;
import com.rdthelper.rdthelper.Repositories.SettingsRepository;
import com.rdthelper.rdthelper.Service.ISettingsService;
import com.rdthelper.rdthelper.Service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class SettingsApi {

    @Autowired
    private ISettingsService settingsService;

    @GetMapping("/settings")
    public ResponseEntity<?> getSettings(){
        try {
            System.out.println(settingsService.findAll());
            return new ResponseEntity<>(settingsService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/settings")
    public ResponseEntity<?> addSettings(@RequestBody Settings settings){
        try{
            Settings s = settingsService.save(settings);
            return new ResponseEntity<>(s, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
