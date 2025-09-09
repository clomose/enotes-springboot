package com.example.Enotes.service;

import com.example.Enotes.entity.User;

public interface JwtService {
    public String generateToken(User user);
}
