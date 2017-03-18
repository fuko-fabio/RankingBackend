package com.nps.ranking;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@SpringBootApplication
@EnableJSONDoc
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
