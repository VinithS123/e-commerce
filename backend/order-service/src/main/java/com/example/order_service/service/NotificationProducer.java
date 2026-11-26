package com.example.order_service.service;

import com.example.order_service.dto.UserDto;
import com.example.order_service.feign.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    final UserServiceClient userServiceClient;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(long userId){

        UserDto userDto = userServiceClient.getUserById(userId);

        kafkaTemplate.send("sms-topic",userDto.getPhoneNumber());

    }

}
