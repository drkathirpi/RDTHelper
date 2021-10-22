package com.rdthelper.rdthelper;

import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;



    @Test
    public void login() throws Exception {
        mockMvc.perform(get("/web/login")).andExpect(redirectedUrl("/web/signup"));
    }

    @Test
    public void checkRedirect() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRdtToken("test");
        user.setShowAll(false);
        User nUser = userService.save(user);
        assertThat(nUser).isNotNull();
        mockMvc.perform(get("/web/login"))
                .andExpect(status().isOk());
    }

}
