package com.example.Enotes.service;

import com.example.Enotes.dto.LoginRequest;
import com.example.Enotes.dto.UserRequest;
import com.example.Enotes.handler.LoginResponse;

public interface UserService {
    public Boolean register(UserRequest userDto, String url) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
