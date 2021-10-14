package com.rdthelper.rdthelper;

import com.rdthelper.rdthelper.Api.TorrentsApi;
import com.rdthelper.rdthelper.Controllers.LoginController;
import com.rdthelper.rdthelper.Controllers.TorrentsController;
import com.rdthelper.rdthelper.Models.Torrents;
import com.rdthelper.rdthelper.Models.User;
import com.rdthelper.rdthelper.Repositories.UserRepository;
import com.rdthelper.rdthelper.Service.TorrentsService;
import com.rdthelper.rdthelper.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = LoginController.class)
class RdthelperApplicationTests {

}
