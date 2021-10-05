package com.rdthelper.rdthelper.Service;

import com.rdthelper.rdthelper.Models.*;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@Service
public class TorrentsService {

    @Autowired
    private UserRepository userRepository;

    private final String BASE_URL = "https://api.real-debrid.com/rest/1.0";
    private RestTemplate restTemplate;

    private String rdtToken;

    public String initRdtToken(){
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(authentication);
        return user.getRdtToken();
    }

    @Bean
    public void initRestTemplate(){
        this.restTemplate = new RestTemplateBuilder().build();
    }

    private HttpHeaders initHeader(){
        if (rdtToken == null){
            rdtToken = initRdtToken();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(rdtToken);
        return httpHeaders;
    }


    public ResponseEntity<?> getAll() throws HttpServerErrorException {
        HttpEntity<?> httpEntity = new HttpEntity<>(initHeader());
        ResponseEntity<?> response = restTemplate.exchange(String.format("%s%s", BASE_URL,"/torrents"), HttpMethod.GET, httpEntity, Object.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<?> getOne(String id) throws HttpServerErrorException {
        HttpEntity<?> httpEntity = new HttpEntity<>(initHeader());
        ResponseEntity<?> response = restTemplate.exchange(String.format("%s%s%s", BASE_URL,"/torrents/info/", id), HttpMethod.GET, httpEntity, Torrents.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteOne(String id) throws HttpServerErrorException {
        HttpEntity<?> httpEntity = new HttpEntity<>(initHeader());
        ResponseEntity<?> response = restTemplate.exchange(String.format("%s%s%s", BASE_URL, "/torrents/delete/", id), HttpMethod.DELETE, httpEntity, String.class);
        return new ResponseEntity<>(new ApiError(0, "OK"), HttpStatus.OK);
    }

    public ResponseEntity<RDTUpload> addTorrent(MultipartFile file) throws HttpServerErrorException, IOException {
        HttpHeaders httpHeaders = initHeader();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<?> httpEntity = new HttpEntity<>(file.getBytes(), httpHeaders);
        return restTemplate.exchange(String.format("%s%s", BASE_URL, "/torrents/addTorrent"), HttpMethod.PUT, httpEntity, RDTUpload.class);
    }

    public ResponseEntity<?> startTorrent(String id) throws HttpServerErrorException {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("files", "all");
        HttpEntity<?> httpEntity = new HttpEntity<>(body, initHeader());
        return restTemplate.exchange(String.format("%s%s%s", BASE_URL, "/torrents/selectFiles/", id), HttpMethod.POST, httpEntity, RDTUpload.class);
    }

    public ResponseEntity<?> debridLink(LinkRequest link) throws HttpServerErrorException {
        HttpHeaders httpHeaders = initHeader();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("link", link.getLink());
        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(String.format("%s%s", BASE_URL, "/unrestrict/link"), HttpMethod.POST, httpEntity, Link.class);
    }
}
