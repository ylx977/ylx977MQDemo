package com.fuzamei.mq.mqConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ylx
 * Created by fuzamei on 2018/6/11.
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queue1(){
        return new Queue("bootQueue1",true);
    }

    @Bean
    public Queue queue2(){
        return new Queue("bootQueue2",true);
    }

    @Bean
    public Queue topicQueue1(){
        return new Queue("boot_topic_queue1",false);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue("boot_topic_queue2",false);
    }
    @Bean
    public Queue miaoshaQueue(){
        return new Queue("miaoshaQueue",false);
    }



    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("boot_topic_exchange");
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("item.#");
    }
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("item.*");
    }

}
