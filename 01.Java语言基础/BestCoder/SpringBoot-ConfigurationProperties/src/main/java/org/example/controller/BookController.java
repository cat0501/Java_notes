package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Value("${lesson}")
    private String lesson;

    @Value("${server.port}")
    private String port;

    @Value("${server.port}")
    private int port2;

    @Autowired
    private Environment environment;
    @GetMapping
    public String getValue(){
        log.info("lesson: {}", lesson);
        log.info("port: {}", port);
        log.info("port2: {}", port2);

        log.info("{}", environment.getProperty("lesson"));
        log.info("{}", environment.getProperty("server.port"));
        return "ok";
    }

}
