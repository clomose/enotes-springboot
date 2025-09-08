package com.example.Enotes.service;

import com.example.Enotes.dto.UserDto;

public interface UserService {
    public Boolean register(UserDto userDto,String url) throws Exception;
}
