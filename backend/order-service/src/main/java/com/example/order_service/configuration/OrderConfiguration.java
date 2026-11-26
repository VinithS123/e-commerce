package com.example.order_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("sms-topic",3,(short)1);
    }

}
