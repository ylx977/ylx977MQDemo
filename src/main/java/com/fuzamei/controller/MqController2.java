package com.fuzamei.controller;

import com.fuzamei.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ylx on 2019/1/15.
 */
@Slf4j
@RestController
@RequestMapping("/mq")
public class MqController2 {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 一个对列，一个发送者，一个接受者
     * @return
     */
    @RequestMapping("/simpleMode")
    public String simpleMode(){
        String message = "hello";
        log.info("发送者(Simple模式[传字符串])：{}",message);
        amqpTemplate.convertAndSend("singleQueue",message);
        return "success";
    }

    /**
     * 一个对列，一个发送者，一个接受者
     * @return
     */
    @RequestMapping("/simpleMode2")
    public String simpleMode2(){
        User user = User.builder().amount(20).name("jack").password("pwd").build();
        log.info("发送者(Simple模式[传对象])：{}",user);
        amqpTemplate.convertAndSend("singleQueue4Object",user);
        return "success";
    }

    /**
     * 一个对列，一个发送者，多个接受者(接受者多劳多得)
     * @return
     */
    @RequestMapping("/workerMode")
    public String workerMode(){
        String message = "hello";
        log.info("发送者(Worker模式[传字符串])");
        for(int i = 0;i<10;i++){
            amqpTemplate.convertAndSend("workerQueue",message + i);
        }
        return "success";
    }

    /**
     * 一个对列，一个发送者，多个接受者(接受者多劳多得)
     * @return
     */
    @RequestMapping("/workerMode2")
    public String workerMode2(){
        log.info("发送者(Worker模式[传对象]");
        for(int i = 0;i<10;i++){
            User user = User.builder().amount(20).name("jack").password("pwd").uid((long)i).build();
            amqpTemplate.convertAndSend("workerQueue4Object",user);
        }
        return "success";
    }

    /**
     * 发布订阅模式 1个消费者，多个对列，多个接受者(每个消费者获得相同的消息)
     * @return
     */
    @RequestMapping("/fanoutMode")
    public String fanoutMode(){
        String message = "hello";
        log.info("发送者(Fanout模式[传字符串]");
        for(int i = 0;i<10;i++){
            //这里routineKey字段一定要加，但是fanout模式下是忽略这个字段的，但是如果不加，接受者就接收不到消息
            amqpTemplate.convertAndSend("fanoutExchange","",message + i);
        }
        return "success";
    }


    /**
     * 路由模式 1个消费者，多个对列，通过传递不同的key，根据路由规则分发到不同对列，并由不同的消费者进行消费(每个对列可以安排多个监听者混合工作模式)
     * @return
     */
    @RequestMapping("/routineMode")
    public String routineMode(){
        String message = "hello";
        log.info("发送者(Direct模式[传字符串]");
        for(int i = 0;i<30;i++){
            String key;
            if(i%3==0){
                //B对列
                key = "orange";
            }else if(i%3==1){
                //A对列
                key = "black";
            }else{
                //A对列
                key = "green";
            }
            amqpTemplate.convertAndSend("directExchange",key,message + " " + i + " " + key);
        }
        return "success";
    }


    /**
     *
     *      * (star) can substitute for exactly one word.
     *      # (hash) can substitute for zero or more words.
     *
     * 主题模式 1个消费者，多个对列，通过传递不同的key，根据路由规则分发到不同对列，并由不同的消费者进行消费(每个对列可以安排多个监听者混合工作模式)
     * @return
     */
    @RequestMapping("/topicMode")
    public String topicMode(){
        String message = "hello";
        log.info("发送者(Topic模式[传字符串]");

        String[] keysX = {"a.orange.b","x.orange.y","m.orange.n","d.orange.e","h.orange.i","*.orange.*"};
        String[] keysY = {"a.b.rabbit","x.y.rabbit","m.n.rabbit","d.e.rabbit","h.i.rabbit","*.*.rabbit"};
        String[] keysZ = {"lazy.####","lazy.sdfsdf","lazy.d","lazy.erheurhwue","lazy.wheadahdashd987"};

        for(int i = 0;i<30;i++){
            String key;
            if(i%3==0){
                //B对列
                key = keysX[(int)(Math.random() * keysX.length)];
            }else if(i%3==1){
                //A对列
                key = keysY[(int)(Math.random() * keysY.length)];
            }else{
                //A对列
                key = keysZ[(int)(Math.random() * keysZ.length)];
            }
//            System.out.println(key);
            amqpTemplate.convertAndSend("topicExchange",key,message + " " + i + " " + key);
        }
        return "success";
    }

}
