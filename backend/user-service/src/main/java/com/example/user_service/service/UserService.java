package com.example.user_service.service;

import com.example.user_service.exception.UserNotFoundExcepion;
import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.UserEntity;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository repository;

    final UserMapper mapper;

    public UserDto addUser(UserDto userDto) {
        UserEntity user = mapper.toUserEntity(userDto);
        return mapper.toUserDto(repository.save(user));
    }

    public UserDto getUserById(Long id) {
        UserEntity user = repository.findById(id).orElseThrow(
                ()-> new UserNotFoundExcepion("User Not Found"));
        return mapper.toUserDto(user);
    }
}
