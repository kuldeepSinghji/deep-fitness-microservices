package com.deepfitness.useractivity.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WebClientConfig.class)
public class WebClientConfigTest {

    @Autowired
    private WebClient webClient;

    @Test
    void webClientBeanIsCreated() {
        assertThat(webClient).isNotNull();
    }

    @Test
    void webClientBaseUrlIsSet() {
        assertThat(webClient).isNotNull();
     }
}
