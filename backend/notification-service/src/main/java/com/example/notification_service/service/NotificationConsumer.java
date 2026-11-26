package com.example.notification_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    final TwilioService twilioService;

    @KafkaListener(
            topics = "sms-topic",
            groupId= "sms-group")
    public void sendNotification(String phoneNumber){
        twilioService.sendMessage(phoneNumber,"Your Order is Successfully placed");
    }

}
