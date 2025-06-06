package com.deepfitness.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.security.oauth2.jwt.Jwt;

@SpringBootTest
@Import({SpringConfig.class, SpringConfigTest.JwtDecoderTestConfig.class})
class SpringConfigTest {
    @Configuration
    static class JwtDecoderTestConfig {
        @Bean
        @Primary
        public ReactiveJwtDecoder reactiveJwtDecoder() {
            // Always returns a dummy Jwt object
            return token -> Mono.just(Jwt.withTokenValue(token)
                    .header("alg", "none")
                    .claim("sub", "test")
                    .build());
        }
    }

    @Autowired
    private SecurityWebFilterChain securityWebFilterChain;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Test
    void securityWebFilterChainBeanExists() {
        assertThat(securityWebFilterChain).isNotNull();
    }

    @Test
    void corsConfigurationSourceBeanExists() {
        assertThat(corsConfigurationSource).isNotNull();
    }
}