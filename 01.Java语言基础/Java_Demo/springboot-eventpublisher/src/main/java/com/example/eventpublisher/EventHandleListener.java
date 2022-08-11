package com.example.eventpublisher;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created on 2021-04-16
 */
@Component
public class EventHandleListener {




    @EventListener
    public void handleEvent(StudentEvent studentEvent) throws Exception {

        System.out.println("哒哒哒");
        System.out.println("哒哒哒");
        System.out.println("哒哒哒");
        System.out.println(studentEvent.getStudent());
    }

}
