package com.example.Enotes.handler;

import com.example.Enotes.dto.UserRequest;
import com.example.Enotes.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private UserResponse user;
    private String token;
}
