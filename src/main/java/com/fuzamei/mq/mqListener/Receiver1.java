package com.fuzamei.mq.mqListener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fuzamei on 2018/6/11.
 */
@Component
@RabbitListener(queues = "bootQueue1")
public class Receiver1 {

    @RabbitHandler
    public void receiver(String message){
        System.out.println("从mq接收到一条消息，然后将其存贮到数据库中去:"+message);
    }
}
