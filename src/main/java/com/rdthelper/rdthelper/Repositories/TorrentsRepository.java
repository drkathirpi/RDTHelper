package com.rdthelper.rdthelper.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdthelper.rdthelper.Exception.RDTResponseError;
import com.rdthelper.rdthelper.Models.ApiError;
import com.rdthelper.rdthelper.Models.File;
import com.rdthelper.rdthelper.Models.RDTUpload;
import com.rdthelper.rdthelper.Models.Torrents;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.H;

@Repository
public class TorrentsRepository {

    private final String BASE_URL = "https://api.real-debrid.com/rest/1.0";
    private RestTemplate restTemplate;

    @Bean
    public void initRestTemplate(){
        this.restTemplate = new RestTemplateBuilder().build();
    }

    private HttpHeaders initHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(System.getenv("RDT_TOKEN"));
        return httpHeaders;
    }


    public ResponseEntity<?> getAll() throws JsonProcessingException {
        try{
            HttpEntity<?> httpEntity = new HttpEntity<>(initHeader());
            ResponseEntity<?> response = restTemplate.exchange(String.format("%s%s", BASE_URL,"/torrents"), HttpMethod.GET, httpEntity, Object.class);
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }catch (HttpClientErrorException e){
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> getOne(String id) throws JsonProcessingException{
        try{
            HttpEntity<?> httpEntity = new HttpEntity<>(initHeader());
            ResponseEntity<?> response = restTemplate.exchange(String.format("%s%s%s", BASE_URL,"/torrents/info/", id), HttpMethod.GET, httpEntity, Torrents.class);
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }catch (HttpClientErrorException e){
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteOne(String id) throws JsonProcessingException {
        try {
            HttpEntity<?> httpEntity = new HttpEntity<>(initHeader());
            ResponseEntity<?> response = restTemplate.exchange(String.format("%s%s%s", BASE_URL, "/torrents/delete/", id), HttpMethod.DELETE, httpEntity, String.class);
            return new ResponseEntity<>(new ApiError(0, "OK"), HttpStatus.OK);
        }catch(HttpClientErrorException e){
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<>(mapper.readValue(e.getResponseBodyAsString(), ApiError.class), HttpStatus.EXPECTATION_FAILED);
        }
    }

    public RDTUpload addTorrent(MultipartFile file) throws IOException {
        HttpHeaders httpHeaders = initHeader();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<?> httpEntity = new HttpEntity<>(file.getBytes(), httpHeaders);
        RDTUpload rdtUpload =  restTemplate.exchange(String.format("%s%s", BASE_URL, "/torrents/addTorrent"), HttpMethod.PUT, httpEntity, RDTUpload.class).getBody();
        System.out.println(rdtUpload);
        return rdtUpload;
    }

    public void acceptAllFile(String id) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("files", "all");
        HttpEntity<?> httpEntity = new HttpEntity<>(body, initHeader());
        restTemplate.exchange(String.format("%s%s%s", BASE_URL, "/torrents/selectFiles/", id), HttpMethod.POST, httpEntity, RDTUpload.class).getBody();
    }
}
