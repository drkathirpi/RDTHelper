package com.rdthelper.rdthelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rdthelper.rdthelper.Api.TorrentsApi;
import com.rdthelper.rdthelper.Controllers.TorrentsController;
import com.rdthelper.rdthelper.Models.Torrents;
import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Service.TokenAuthService;
import com.rdthelper.rdthelper.Service.TorrentsService;
import com.rdthelper.rdthelper.Service.UserService;
import jdk.nashorn.internal.parser.Token;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class LoginUsageTest {

    @InjectMocks
    private TorrentsController torrentsController;

    @InjectMocks
    private TorrentsApi torrentsApi;

    private MockMvc mockMvc;




    private HttpHeaders httpHeaders;

    private final String username = "test";

    @BeforeEach
    public void init() throws Exception {

        MockitoAnnotations.openMocks(this);


        this.httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization",
                "Bearer " + TokenAuthService.generateToken(username));
    }




    @Test
    public void shouldGetHomeModelView() throws Exception {
        mockMvc.perform(get("/web/home")
                .headers(httpHeaders)).andExpect(status().isOk()); //200 code
    }

    @Test
    public void shouldGetTorrentsModelView() throws Exception {
        mockMvc.perform(get("/web/torrents")
                .headers(httpHeaders)).andExpect(status().isOk()); //200 code;
    }
}
