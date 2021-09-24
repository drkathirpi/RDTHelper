package com.rdthelper.rdthelper.Service;


import com.rdthelper.rdthelper.Models.Settings;
import com.rdthelper.rdthelper.Repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService implements ISettingsService{

    @Autowired
    private SettingsRepository settingsRepository;


    @Override
    public List<Settings> findAll(){
        return settingsRepository.findAll();
    }


    @Override
    public Settings save(Settings s){
        return settingsRepository.save(s);

    }


}
