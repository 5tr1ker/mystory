package com.team.mystory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyStoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyStoryApplication.class, args);
    }

}
