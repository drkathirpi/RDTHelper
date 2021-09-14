package com.rdthelper.rdthelper;

import com.rdthelper.rdthelper.Service.UploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

import javax.annotation.Resource;

@SpringBootApplication
public class RdthelperApplication extends WebMvcAutoConfiguration implements CommandLineRunner {

	@Resource
	UploadService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(RdthelperApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		uploadService.deleteAll();
		uploadService.init();
	}

}
