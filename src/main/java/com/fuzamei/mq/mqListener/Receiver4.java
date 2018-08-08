package com.fuzamei.mq.mqListener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by fuzamei on 2018/8/5.
 */
@Component
@RabbitListener(queues = "boot_topic_queue1")
public class Receiver4 {

    @RabbitHandler
    public void receiver(String message){
        System.out.println("Rev4:"+message+"-------------->2");
    }

}
