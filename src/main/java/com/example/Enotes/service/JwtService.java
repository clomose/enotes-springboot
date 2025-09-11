package com.example.Enotes.service;

import com.example.Enotes.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String generateToken(User user);

    public String extractEmail(String  token);

    public Boolean validateToken(String token, UserDetails userDetails);
}
