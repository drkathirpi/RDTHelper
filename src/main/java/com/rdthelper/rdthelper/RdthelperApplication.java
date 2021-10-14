package com.rdthelper.rdthelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class RdthelperApplication {

	private final static Logger logger = Logger.getLogger(RdthelperApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(RdthelperApplication.class, args);
	}


}
