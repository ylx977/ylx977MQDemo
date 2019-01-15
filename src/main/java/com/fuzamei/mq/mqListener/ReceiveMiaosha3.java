package com.fuzamei.mq.mqListener;

import com.fuzamei.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;

/**
 * @author ylx
 * Created by fuzamei on 2018/8/7.
 */
@Component
@Slf4j
@RabbitListener(queues = "miaoshaQueue3")
public class ReceiveMiaosha3 {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisPool jedisPool;

    @RabbitHandler
    public void receiver(String message){
        Jedis jedis = jedisPool.getResource();
        try {
            long i = Long.parseLong(message);
            System.err.println(new Date()+":用户"+i+"成功抢到一件商品");
            int success = userMapper.updateUserAmountById(i);
            if(success==0){
                log.error("更新用户amount失败");
            }
        }finally {
            jedis.close();
        }
    }
}
