package com.deepfitness.deepaiservice.service;
import com.deepfitness.deepaiservice.service.impl.GeminiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import reactor.core.publisher.Mono;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GeminiServiceImplTest {

    private WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;
    private WebClient.RequestBodySpec requestBodySpec;
    private WebClient.ResponseSpec responseSpec;

    private GeminiServiceImpl geminiService;

    @BeforeEach
    void setUp() {
        webClientBuilder = mock(WebClient.Builder.class);
        webClient = mock(WebClient.class);
        requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        requestBodySpec = mock(WebClient.RequestBodySpec.class);
        responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);

        geminiService = new GeminiServiceImpl(webClientBuilder);
        // Set private fields using reflection
        setField(geminiService, "geminiApiURL", "http://fake-url/");
        setField(geminiService, "getGeminiApiKey", "fake-key");
    }

    @Test
    void testGetGeminiRequestBody() {
        String userAsk = "Tell me a joke";
        Map<String, Object> body = invokeGetGeminiRequestBody(geminiService, userAsk);
        assertNotNull(body);
        assertTrue(body.containsKey("contents"));
    }

    @Test
    void testGetGeminiAPIHeaders() {
        HttpHeaders headers = invokeGetGeminiAPIHeaders(geminiService);
        assertNotNull(headers);
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
    }

    @Test
    void testCallGeminiAPI_ReturnsResponse() {
        String userAsk = "Hello Gemini!";
        String expectedResponse = "{\"response\":\"Hi!\"}";

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        // Add this line to chain the mocks correctly:
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(String.class))).thenReturn(Mono.just(expectedResponse));

        String result = geminiService.callGeminiAPI(userAsk);

        assertEquals(expectedResponse, result);
        verify(webClient).post();
        verify(requestBodyUriSpec).uri(contains("fake-key"));
        verify(requestBodySpec).headers(any());
        verify(requestBodySpec).bodyValue(any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(String.class);
    }

    // --- Helper methods for reflection and private method invocation ---

    private static void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> invokeGetGeminiRequestBody(GeminiServiceImpl service, String userAsk) {
        try {
            var method = GeminiServiceImpl.class.getDeclaredMethod("getGeminiRequestBody", String.class);
            method.setAccessible(true);
            return (Map<String, Object>) method.invoke(service, userAsk);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpHeaders invokeGetGeminiAPIHeaders(GeminiServiceImpl service) {
        try {
            var method = GeminiServiceImpl.class.getDeclaredMethod("getGeminiAPIHeaders");
            method.setAccessible(true);
            return (HttpHeaders) method.invoke(service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
