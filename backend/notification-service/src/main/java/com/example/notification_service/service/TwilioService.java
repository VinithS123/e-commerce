package com.example.notification_service.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;

public class TwilioService{

    @Value("${media.twilio.auth-token}")
    private String AUTH_TOKEN;

    @Value("${media.twilio.account-sid}")
    private String ACCOUNT_SID;

    public void sendMessage(String phoneNumber, String message) {

        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);

        Message msg = Message
                //to 917483969238
                .creator(new com.twilio.type.PhoneNumber(phoneNumber),
                        //from +12294952281
                        new com.twilio.type.PhoneNumber("+12294952281"),
                        message)
                .create();
        System.out.println(msg.getStatus());

    }
}

