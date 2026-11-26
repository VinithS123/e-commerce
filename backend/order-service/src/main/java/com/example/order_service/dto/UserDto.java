package com.example.order_service.dto;

import lombok.Data;

@Data
public class UserDto {

    private long userId;

    private String userName;

    private String password;

    private String phoneNumber;

}
