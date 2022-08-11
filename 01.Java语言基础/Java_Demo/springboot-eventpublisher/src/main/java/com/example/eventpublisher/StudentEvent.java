package com.example.eventpublisher;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * 事件类
 * Created on 2021-04-16
 */
@Getter
public class StudentEvent extends ApplicationEvent {


    private Student student;

    public StudentEvent(Object source,Student student) {
        super(source);
        this.student = student;
    }
}
