package com.rdthelper.rdthelper;

import com.rdthelper.rdthelper.Models.Torrents;
import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", password = "test")
public class LoginUsageTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;


    @Test
    public void shouldGetHomeModelView() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()); //200 code
    }

    @Test
    public void shouldGetTorrentsModelView() throws Exception {
        mockMvc.perform(get("/torrents")).andReturn().getResponse();
    }

   // @Test
   // public void shouldGetSettingsModelView() throws Exception{
   //     mockMvc.perform(get("/settings")).andReturn().getResponse();
   // }



}
