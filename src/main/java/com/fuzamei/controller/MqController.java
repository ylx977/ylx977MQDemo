package com.fuzamei.controller;

import com.fuzamei.constant.RedisPrefix;
import com.fuzamei.entity.User;
import com.fuzamei.util.RedisUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.CorrelationDataPostProcessor;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * @author ylx
 * Created by fuzamei on 2018/8/5.
 */
@RestController
@RequestMapping("/api")
public class MqController {

    private static final int[] uids = {1,2,3,4,5,6,7,8,9,10,
                                       11,12,13,14,15,16,17,18,19,20,
                                       21,22,23,24,25,26,27,28,29,30,
                                       31,32,33,34,35,36,37,38,39,40,
                                       41,42,43,44,45,46,47,48,49,50,
                                       51,52,53,54,55,56,57,58,59,60,
                                       61,62,63,64,65,66,67,68,69,70,
                                       71,72,73,74,75,76,77,78,79,80,
                                       81,82,83,84,85,86,87,88,89,90,
                                       91,92,93,94,95,96,97,98,99,100};

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/testSimple")
    public String testSimple(){
        amqpTemplate.convertAndSend("bootQueue1","test");
        return "success";
    }

    @RequestMapping("/testWork")
    public String testWork() throws InterruptedException {
        for (int i=0;i<20;i++){
            amqpTemplate.convertAndSend("bootQueue2","test"+i);
            Thread.sleep(10*i);
        }
        return "success";
    }

    @RequestMapping("/testTopic1")
    public String testTopic1() throws InterruptedException {
        amqpTemplate.convertAndSend("boot_topic_exchange","item.wocao","两个都有");
        return "success";
    }
    @RequestMapping("/testTopic2")
    public String testTopic2() throws InterruptedException {
        amqpTemplate.convertAndSend("boot_topic_exchange","item.wocao.haha","只有一个有");
        return "success";
    }

    @RequestMapping("/miaosha")
    public String maiosha() throws UnsupportedEncodingException {
        Message message = amqpTemplate.receive("test_queue");
        if(message==null){
            return "fail";
        }else{
            String success = new String(message.getBody(),"utf-8");
            return success;
        }
    }

    /**
     * 一个用户只能抢一个商品的秒杀业务
     * @param user
     * @return
     */
    @RequestMapping("/miaosha2")
    public String miaosha2(@RequestBody User user){
        long uid = user.getUid();
        Message message = amqpTemplate.receive("test_queue");
        if(message==null){
            return "fail";
        }else{
            boolean flag = redisUtil.setNx(RedisPrefix.MIAOSHA + uid, String.valueOf(uid), 600L * 1000);
            if(!flag){
                amqpTemplate.convertAndSend("test_queue","success");
                return "您已经抢过该商品了";
            }else{
                amqpTemplate.convertAndSend("miaoshaQueue",String.valueOf(uid));
                redisUtil.decrOne("total");
                return "success of "+uid;
            }
            //以下这段在else中的代码会出现非原子性操作(也就某一个用户会出现2件商品的情况)
//            if(redisUtil.hasKey(RedisPrefix.MIAOSHA+uid)){
//                amqpTemplate.convertAndSend("test_queue","success");
//                return "您已经抢过该商品了";
//            }
//            redisUtil.set(RedisPrefix.MIAOSHA+uid,uid,10, TimeUnit.MINUTES);
//            amqpTemplate.convertAndSend("miaoshaQueue",String.valueOf(uid));
//            return "success of "+uid;
        }
    }

}
