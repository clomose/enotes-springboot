package com.example.Enotes.endpoints;

import com.example.Enotes.dto.LoginRequest;
import com.example.Enotes.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication",description = "All the user Authentication APIs")
public interface AuthEndpoint {

    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Register Success"), @ApiResponse(responseCode = "500", description = "Interna Server error"), @ApiResponse(responseCode = "400", description = "Bad Request") })
    @Operation(summary = "User register endpoints")
    @PostMapping("/")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception;

    @Operation(summary = "User login endpoints")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;
}
