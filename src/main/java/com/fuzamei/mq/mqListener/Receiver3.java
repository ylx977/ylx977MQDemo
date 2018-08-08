package com.fuzamei.mq.mqListener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by fuzamei on 2018/8/5.
 */
@Component
@RabbitListener(queues = "bootQueue2")
public class Receiver3 /*implements ChannelAwareMessageListener*/ {

    @RabbitHandler
    public void receiver(String message){
        System.out.println("Rev2:"+message+"-------------->2");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        String msg = new String(body);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消费者2消费了信息："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }*/

}
