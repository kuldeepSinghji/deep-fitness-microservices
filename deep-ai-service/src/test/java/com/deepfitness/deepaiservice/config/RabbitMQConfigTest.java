package com.deepfitness.deepaiservice.config;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class RabbitMQConfigTest {

    @Test
    void testUserActivityQueue() {
        RabbitMQConfig config = new RabbitMQConfig();
        ReflectionTestUtils.setField(config, "queueName", "test-queue");
        Queue queue = config.userActivityQueue();
        assertNotNull(queue);
        assertEquals("test-queue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void testUserActivityExchange() {
        RabbitMQConfig config = new RabbitMQConfig();
        ReflectionTestUtils.setField(config, "exchangeName", "test-exchange");
        DirectExchange exchange = config.userActivityExchange();
        assertNotNull(exchange);
        assertEquals("test-exchange", exchange.getName());
    }

    @Test
    void testUserActivityBinding() {
        RabbitMQConfig config = new RabbitMQConfig();
        ReflectionTestUtils.setField(config, "queueName", "test-queue");
        ReflectionTestUtils.setField(config, "exchangeName", "test-exchange");
        ReflectionTestUtils.setField(config, "routingName", "test-routing");

        Queue queue = config.userActivityQueue();
        DirectExchange exchange = config.userActivityExchange();
        Binding binding = config.userActivityBinding(queue, exchange);

        assertNotNull(binding);
        assertEquals("test-queue", binding.getDestination());
        assertEquals("test-exchange", binding.getExchange());
        assertEquals("test-routing", binding.getRoutingKey());
    }

    @Test
    void testJsonMessageConverter() {
        RabbitMQConfig config = new RabbitMQConfig();
        MessageConverter converter = config.jsonMessageConverter();
        assertNotNull(converter);
        assertEquals("org.springframework.amqp.support.converter.Jackson2JsonMessageConverter", converter.getClass().getName());
    }
}