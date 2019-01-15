package com.fuzamei.mq.rpcListener;

import com.fuzamei.mq.mqConfig.RabbitMqRPCConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by ylx on 2019/1/15.
 */
@Component
@Slf4j
public class RpcListener {

    @RabbitHandler
    @RabbitListener(queues = RabbitMqRPCConfig.QUEUE_SYNC_RPC)
    public String process(String message) {
        int millis = (int) (Math.random() * 2 * 10000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
        return message + " sleep for " + millis + " ms";
    }

}
