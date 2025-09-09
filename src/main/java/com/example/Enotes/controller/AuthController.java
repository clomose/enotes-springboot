package com.example.Enotes.controller;

import com.example.Enotes.dto.LoginRequest;
import com.example.Enotes.dto.UserDto;
import com.example.Enotes.handler.LoginResponse;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?>  registerUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception
    {
        String url = CommonUtil.getUrl(request);
        Boolean register = userService.register(userDto,url);
        if(register){
            return CommonUtil.createBuildResponse("Register Successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createBuildResponseMessage("Registration Failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception{
        LoginResponse loginResponse =  userService.login(loginRequest);
        if (ObjectUtils.isEmpty(loginResponse)){
            return CommonUtil.createErrorResponseMessage("Invalid credential",HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuildResponse(loginResponse,HttpStatus.OK);
    }

}
