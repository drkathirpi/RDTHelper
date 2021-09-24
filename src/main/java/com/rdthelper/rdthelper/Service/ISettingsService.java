package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.Settings;

import java.util.List;

public interface ISettingsService {

    List<Settings> findAll();
    Settings save(Settings s);


}
