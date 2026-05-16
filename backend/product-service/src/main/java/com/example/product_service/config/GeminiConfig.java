package com.example.product_service.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Value("${ai.gemini.api-key}")
    private String geminiAPiKey;

    @Bean
    public Client geminiClient(){
        return Client.builder().apiKey(geminiAPiKey).build();
    }
}
