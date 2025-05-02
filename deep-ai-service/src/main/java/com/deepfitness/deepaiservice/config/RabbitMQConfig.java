package com.deepfitness.deepaiservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Value("${rabbitmq.routing}")
    private String routingName;

    @Bean
    public Queue userActivityQueue(){
        return new Queue(queueName,true);
    }

    @Bean
    DirectExchange userActivityExchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding userActivityBinding(Queue userActivityQueue, DirectExchange userActivityExchange){
        return BindingBuilder.bind(userActivityQueue)
                .to(userActivityExchange).with(routingName);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
