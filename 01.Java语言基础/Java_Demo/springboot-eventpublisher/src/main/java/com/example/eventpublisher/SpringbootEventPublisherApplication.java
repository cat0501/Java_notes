package com.example.eventpublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootEventPublisherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootEventPublisherApplication.class, args);
        EventHandleListener eventHandleListener = (EventHandleListener) context.getBean("eventHandleListener");
    }

}
