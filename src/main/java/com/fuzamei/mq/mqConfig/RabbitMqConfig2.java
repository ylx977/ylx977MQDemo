package com.fuzamei.mq.mqConfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置对列类
 * @author ylx
 * Created by fuzamei on 2018/6/11.
 */
@Configuration
public class RabbitMqConfig2 {

    //=========================简单模式对列==============================
    @Bean
    public Queue singleQueue(){
        //简单模式传字符串
        return new Queue("singleQueue",true);
    }
    @Bean
    public Queue singleQueue4Object(){
        //简单模式传对象
        return new Queue("singleQueue4Object",true);
    }
    //=========================简单模式对列==============================


    //=========================工作模式对列==============================
    @Bean
    public Queue workerQueue(){
        //工作者模式传字符串
        return new Queue("workerQueue",true);
    }
    @Bean
    public Queue workerQueue4Object(){
        //工作者模式传对象
        return new Queue("workerQueue4Object",true);
    }
    //=========================工作模式对列==============================


    //=========================发布订阅模式对列==============================
    @Bean
    public Queue fanoutQueueA(){
        //发布订阅模式传字符串
        return new Queue("fanoutQueueA",true);
    }
    @Bean
    public Queue fanoutQueueB(){
        //发布订阅模式传对象
        return new Queue("fanoutQueueB",true);
    }
    @Bean
    public Queue fanoutQueueC(){
        //发布订阅模式传对象
        return new Queue("fanoutQueueC",true);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        //创建exchange
        return new FanoutExchange("fanoutExchange");
    }

    //分别给三个队列指定exchange,这里使用了A、B、C三个队列绑定到Fanout交换机上面，发送端的routing_key写任何字符都会被忽略（但是routineKey必须要写）：
    @Bean
    public Binding bindingFanoutExchangeA(Queue fanoutQueueA, FanoutExchange fanoutExchange){
        //这两种写法都可以，带参数必须和对列的方法名字必须一致
//        return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
        return BindingBuilder.bind(fanoutQueueA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanoutExchangeB(Queue fanoutQueueB,FanoutExchange fanoutExchange){
//        return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
        return BindingBuilder.bind(fanoutQueueB()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeC(Queue fanoutQueueC, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(fanoutQueueC).to(fanoutExchange);
        return BindingBuilder.bind(fanoutQueueC()).to(fanoutExchange());
    }

    //=========================发布订阅模式对列==============================


    //=========================路由模式对列=================================

    @Bean
    public Queue routineQueueA(){
        //路由模式对列
        return new Queue("routineQueueA",true);
    }

    @Bean
    public Queue routineQueueB(){
        //路由模式对列
        return new Queue("routineQueueB",true);
    }

    @Bean
    public DirectExchange directExchange() {
        //创建DirectExchange
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding bindingDirectExchangeA(Queue routineQueueA,DirectExchange directExchange){
        return BindingBuilder.bind(routineQueueA).to(directExchange).with("black");
    }
    @Bean
    public Binding bindingDirectExchangeA1(Queue routineQueueA,DirectExchange directExchange){
        return BindingBuilder.bind(routineQueueA).to(directExchange).with("green");
    }
    @Bean
    public Binding bindingDirectExchangeB(Queue routineQueueB,DirectExchange directExchange){
        return BindingBuilder.bind(routineQueueB).to(directExchange).with("orange");
    }

    //=========================路由模式对列=================================


    //=========================主题模式对列=================================

    @Bean
    public Queue topicQueueA(){
        //路由模式对列
        return new Queue("topicQueueA",true);
    }

    @Bean
    public Queue topicQueueB(){
        //路由模式对列
        return new Queue("topicQueueB",true);
    }

    @Bean
    public TopicExchange topicExchange2() {
        //创建TopicExchange
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingTopicExchangeA(){
        return BindingBuilder.bind(topicQueueA()).to(topicExchange2()).with("*.*.rabbit");
    }
    @Bean
    public Binding bindingTopicExchangeA1(){
        return BindingBuilder.bind(topicQueueA()).to(topicExchange2()).with("lazy.#");
    }
    @Bean
    public Binding bindingTopicExchangeB(){
        return BindingBuilder.bind(topicQueueB()).to(topicExchange2()).with("*.orange.*");
    }

    //=========================主题模式对列=================================

}
