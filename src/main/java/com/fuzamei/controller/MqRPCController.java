package com.fuzamei.controller;

import com.fuzamei.mq.mqConfig.RabbitMqRPCConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ylx
 * Created by ylx on 2019/1/15.
 */
@Slf4j
@RestController
@RequestMapping("/rpc")
public class MqRPCController {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @RequestMapping("/rpcRequest")
    public String sendRpcRequest(){
        long start = System.currentTimeMillis();
        String result = (String) amqpTemplate.convertSendAndReceive(RabbitMqRPCConfig.QUEUE_SYNC_RPC, "rpc-request");
        long end = System.currentTimeMillis();
        log.info("同步等待时间:{}ms",end - start);
        log.info("服务端返回的消息：{}",result);
        return "success";
    }

}
