package com.group13.sysc4806;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class Sysc4806Application {

    private static final Logger log = LoggerFactory.getLogger(Sysc4806Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Sysc4806Application.class, args);
    }

}
