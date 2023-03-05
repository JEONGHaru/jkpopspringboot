package com.haru.jkpop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JkpopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JkpopApplication.class, args);
    }

}
