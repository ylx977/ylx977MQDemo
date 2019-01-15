package com.fuzamei.mq.mqConfig;

/**
 * Created by ylx on 2019/1/15.
 */

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 配置对列类
 * @author ylx
 * Created by fuzamei on 2018/6/11.
 */
@Configuration
public class RabbitMqRPCConfig {

    /**
     * 同步RPC队列
     */
    public static final String QUEUE_SYNC_RPC = "rpc.sync";

    /**
     * 异步RPC队列，使用临时回复队列，或者使用“Direct reply-to”特性
     */
    public static final String QUEUE_ASYNC_RPC = "rpc.async";

    /**
     * 异步RPC队列，每个客户端使用不同的固定回复队列，需要额外提供correlationId以关联请求和响应
     */
    public static final String QUEUE_ASYNC_RPC_WITH_FIXED_REPLY = "rpc.with.fixed.reply";

    @Bean
    public Queue syncRPCQueue() {
        return new Queue(QUEUE_SYNC_RPC);
    }

//    @Bean
//    public Queue asyncRPCQueue() {
//        return new Queue(QUEUE_ASYNC_RPC);
//    }
//
//    @Bean
//    public Queue fixedReplyRPCQueue() {
//        return new Queue(QUEUE_ASYNC_RPC_WITH_FIXED_REPLY);
//    }

//    @Bean
//    public Queue repliesQueue() {
//        return new AnonymousQueue();
//    }
//
//    @Bean
//    @Primary
//    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames(repliesQueue().getName());
//        return container;
//    }
//
//    @Bean
//    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template, SimpleMessageListenerContainer container) {
//        return new AsyncRabbitTemplate(template, container);
//    }

}
