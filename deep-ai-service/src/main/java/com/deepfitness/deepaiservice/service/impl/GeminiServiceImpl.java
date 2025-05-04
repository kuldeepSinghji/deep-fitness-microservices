package com.deepfitness.deepaiservice.service.impl;

import com.deepfitness.deepaiservice.service.GeminiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Slf4j
public class GeminiServiceImpl implements GeminiService {

    private final WebClient webClient;

    @Value("${GEMINI_API_URL}")
    private String geminiApiURL;

    @Value("${GEMINI_API_KEY}")
    private String getGeminiApiKey;

    public GeminiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private  Map<String,Object> getGeminiRequestBody(String userAsk){
        return Map.of(
                "contents",new Object[]{
                        Map.of(
                                "parts",new Object[]{
                                        Map.of("text", userAsk)
                                }
                        )
                }
        );
    }

    private HttpHeaders getGeminiAPIHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public String callGeminiAPI(String userAsk){
        HttpHeaders headers = getGeminiAPIHeaders();
        return webClient.post()
                .uri(geminiApiURL+getGeminiApiKey)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(getGeminiRequestBody(userAsk))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

