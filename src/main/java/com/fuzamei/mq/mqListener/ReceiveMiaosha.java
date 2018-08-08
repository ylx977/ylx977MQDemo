package com.fuzamei.mq.mqListener;

import com.fuzamei.entity.User;
import com.fuzamei.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ylx
 * Created by fuzamei on 2018/8/7.
 */
@Component
@Slf4j
@RabbitListener(queues = "miaoshaQueue")
public class ReceiveMiaosha {

    @Autowired
    private UserMapper userMapper;

    @RabbitHandler
    public void receiver(String message){
        long i = Long.parseLong(message);
        System.err.println("用户"+i+"成功抢到一件商品");
        int success = userMapper.updateUserAmountById(i);
        if(success==0){
            log.error("更新用户amount失败");
        }
    }
}
