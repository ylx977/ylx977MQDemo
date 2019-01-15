package com.fuzamei.mq.mqListener2;

import com.fuzamei.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @RabbitListener可以加在类上也可以加在方法上
 * Created by ylx on 2019/1/15.
 */
@Component
@Slf4j
public class SingleReceiver {

    //=================================简单模式单接收↓↓↓↓========================================
    /**
     * 简单模式接收字符串
     * 消息处理器（接收String类型）
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = "singleQueue")
    public void process(String message){
        log.info("接受者(SimpleMode[接收字符串])：{}",message);
    }

    /**
     * 简单模式接收对象
     * 消息处理器（接收User类型）
     * @param user
     */
    @RabbitHandler
    @RabbitListener(queues = "singleQueue4Object")
    public void process2(User user){
        log.info("接受者2(SimpleMode[接收对象])：{}",user.toString());
    }
    //=================================简单模式单接收↑↑↑↑========================================


    //=================================工作模式多接收↓↓↓↓========================================
    /**
     * 工作者模式接收字符串
     * 消息处理器（接收String类型）
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = "workerQueue")
    public void process3_0(String message){
        log.info("接受者0(WorkerMode[接收字符串])：{}",message);
    }
    /**
     * 工作者模式接收字符串
     * 消息处理器（接收String类型）
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = "workerQueue")
    public void process3_1(String message){
        log.info("接受者1(WorkerMode[接收字符串])：{}",message);
    }
    /**
     * 工作者模式接收字符串
     * 消息处理器（接收String类型）
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = "workerQueue")
    public void process3_2(String message){
        log.info("接受者2(WorkerMode[接收字符串])：{}",message);
    }

    /**
     * 工作者模式接收对象
     * 消息处理器（接收User类型）
     * @param user
     */
    @RabbitHandler
    @RabbitListener(queues = "workerQueue4Object")
    public void process4_0(User user){
        log.info("接受者0(WorkerMode[接收对象])：{}",user.toString());
    }
    /**
     * 工作者模式接收对象
     * 消息处理器（接收User类型）
     * @param user
     */
    @RabbitHandler
    @RabbitListener(queues = "workerQueue4Object")
    public void process4_1(User user){
        log.info("接受者1(WorkerMode[接收对象])：{}",user.toString());
    }
    /**
     * 工作者模式接收对象
     * 消息处理器（接收User类型）
     * @param user
     */
    @RabbitHandler
    @RabbitListener(queues = "workerQueue4Object")
    public void process4_2(User user){
        log.info("接受者2(WorkerMode[接收对象])：{}",user.toString());
    }
    //=================================工作模式多接收↑↑↑↑========================================


    //=================================发布订阅fanout模式多接收↓↓↓↓========================================

    @RabbitHandler
    @RabbitListener(queues = "fanoutQueueA")
    public void process5A(String message){
        log.info("接受者A(FanoutMode[接收字符串])：{}",message);
    }
    @RabbitHandler
    @RabbitListener(queues = "fanoutQueueB")
    public void process5B(String message){
        log.info("接受者B(FanoutMode[接收字符串])：{}",message);
    }
    @RabbitHandler
    @RabbitListener(queues = "fanoutQueueC")
    public void process5C(String message){
        log.info("接受者C(FanoutMode[接收字符串])：{}",message);
    }

    //=================================发布订阅fanout模式多接收↑↑↑↑========================================


    //=================================路由routine模式多接收↓↓↓↓========================================

    @RabbitHandler
    @RabbitListener(queues = "routineQueueA")
    public void process6A(String message){
        log.info("接受者A(DirectMode[接收字符串])：{}",message);
    }
    @RabbitHandler
    @RabbitListener(queues = "routineQueueA")
    public void process6A2(String message){
        log.info("接受者A2(DirectMode[接收字符串])：{}",message);
    }
    @RabbitHandler
    @RabbitListener(queues = "routineQueueB")
    public void process6B(String message){
        log.info("接受者B(DirectMode[接收字符串])：{}",message);
    }
    @RabbitHandler
    @RabbitListener(queues = "routineQueueB")
    public void process6B2(String message){
        log.info("接受者B2(DirectMode[接收字符串])：{}",message);
    }

    //=================================路由routine模式多接收↑↑↑↑========================================


    //=================================主题topic模式多接收↓↓↓↓========================================

    @RabbitHandler
    @RabbitListener(queues = "topicQueueA")
    public void process7A(String message){
        log.info("接受者A(TopicMode[接收字符串])：{}",message);
    }
    @RabbitHandler
    @RabbitListener(queues = "topicQueueB")
    public void process7B(String message){
        log.info("接受者B(TopicMode[接收字符串])：{}",message);
    }

    //=================================主题topic模式多接收↑↑↑↑========================================
}
