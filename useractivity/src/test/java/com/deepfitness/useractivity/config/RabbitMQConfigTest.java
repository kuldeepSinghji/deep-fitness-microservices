package com.deepfitness.useractivity.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RabbitMQConfig.class)
@TestPropertySource(properties = {
        "rabbitmq.exchange=test-exchange",
        "rabbitmq.queue=test-queue",
        "rabbitmq.routing=test-routing"
})
public class RabbitMQConfigTest {

    @Autowired
    private Queue userActivityQueue;

    @Autowired
    private DirectExchange userActivityExchange;

    @Autowired
    private Binding userActivityBinding;

    @Autowired
    private MessageConverter jsonMessageConverter;

    @Test
    void queueIsCreatedWithCorrectNameAndDurability() {
        assertThat(userActivityQueue.getName()).isEqualTo("test-queue");
        assertThat(userActivityQueue.isDurable()).isTrue();
    }

    @Test
    void exchangeIsCreatedWithCorrectName() {
        assertThat(userActivityExchange.getName()).isEqualTo("test-exchange");
    }

    @Test
    void bindingBindsCorrectQueueExchangeAndRoutingKey() {
        assertThat(userActivityBinding.getExchange()).isEqualTo("test-exchange");
        assertThat(userActivityBinding.getDestination()).isEqualTo("test-queue");
        assertThat(userActivityBinding.getRoutingKey()).isEqualTo("test-routing");
    }

    @Test
    void messageConverterIsJackson2JsonMessageConverter() {
        assertThat(jsonMessageConverter).isInstanceOf(Jackson2JsonMessageConverter.class);
    }
}
