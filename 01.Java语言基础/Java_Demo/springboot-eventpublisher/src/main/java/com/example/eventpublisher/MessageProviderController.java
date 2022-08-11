package com.example.eventpublisher;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2021-04-16
 */
@RestController
public class MessageProviderController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;   //org.springframework.context

    @GetMapping("/insert")
    public Object insertMessage() {

        // 注册用户
        Student student = new Student();
        student.setId(1);
        student.setName(LocalDateTime.now().toString());

        //源码分析:为什么能走到自定义的EventHandleLister,其实就是下面的一行代码，跟一下就行
        eventPublisher.publishEvent(new StudentEvent(this, student));

        return 1;

    }
}
