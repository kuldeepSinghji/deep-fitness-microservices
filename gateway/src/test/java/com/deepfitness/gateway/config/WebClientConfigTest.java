package com.deepfitness.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(WebClientConfig.class)
class WebClientConfigTest {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private WebClient userDetailServiceWebClient;

    @Test
    void webClientBuilderBeanExists() {
        assertThat(webClientBuilder).isNotNull();
    }

    @Test
    void userDetailServiceWebClientBeanExists() {
        assertThat(userDetailServiceWebClient).isNotNull();
    }
}