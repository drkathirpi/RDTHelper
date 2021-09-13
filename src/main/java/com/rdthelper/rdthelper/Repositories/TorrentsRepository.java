package com.rdthelper.rdthelper.Repositories;

import com.rdthelper.rdthelper.Models.Torrents;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TorrentsRepository {


    public Torrents[] getAll(){
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(System.getenv("RDT_TOKEN"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange("https://api.real-debrid.com/rest/1.0/torrents", HttpMethod.GET, httpEntity, Torrents[].class).getBody();
    }
}
