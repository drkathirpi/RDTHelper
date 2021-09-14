package com.rdthelper.rdthelper.Repositories;

import com.rdthelper.rdthelper.Models.File;
import com.rdthelper.rdthelper.Models.RDTUpload;
import com.rdthelper.rdthelper.Models.Torrents;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TorrentsRepository {

    private final String BASE_URL = "https://api.real-debrid.com/rest/1.0";
    private RestTemplate restTemplate;


    @Bean
    public void initRestTemplate(){
        this.restTemplate = new RestTemplateBuilder().build();
    }


    public Torrents[] getAll(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(System.getenv("RDT_TOKEN"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(String.format("%s%s", BASE_URL,"/torrents"), HttpMethod.GET, httpEntity, Torrents[].class).getBody();
    }

    public Torrents getOne(String id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(System.getenv("RDT_TOKEN"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(String.format("%s%s%s", BASE_URL,"/torrents/info/", id), HttpMethod.GET, httpEntity, Torrents.class).getBody();
    }

    public RDTUpload addTorrent(MultipartFile file) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(System.getenv("RDT_TOKEN"));
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<?> httpEntity = new HttpEntity<>(file.getBytes(), httpHeaders);
        RDTUpload rdtUpload =  restTemplate.exchange(String.format("%s%s", BASE_URL, "/torrents/addTorrent"), HttpMethod.PUT, httpEntity, RDTUpload.class).getBody();
        System.out.println(rdtUpload);
        return rdtUpload;
    }

    public void acceptAllFile(String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(System.getenv("RDT_TOKEN"));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("files", "all");
        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);
        restTemplate.exchange(String.format("%s%s%s", BASE_URL, "/torrents/selectFiles/", id), HttpMethod.POST, httpEntity, RDTUpload.class).getBody();
    }
}
