package com.fuzamei.mq.mqListener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by fuzamei on 2018/8/5.
 */
@Component
@RabbitListener(queues = "boot_topic_queue2")
public class Receiver5 {

    @RabbitHandler
    public void receiver(String message){
        System.out.println("Rev5:"+message+"-------------->2");
    }

}
