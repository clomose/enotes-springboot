package com.example.Enotes.controller;

import com.example.Enotes.dto.LoginRequest;
import com.example.Enotes.dto.UserRequest;
import com.example.Enotes.endpoints.AuthEndpoint;
import com.example.Enotes.handler.LoginResponse;
import com.example.Enotes.service.AuthService;
import com.example.Enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AuthController implements AuthEndpoint {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<?>  registerUser(UserRequest userDto, HttpServletRequest request) throws Exception
    {
        log.info("AuthController : verifyUserAccount() : Execution Start");
        String url = CommonUtil.getUrl(request);
        Boolean register = authService.register(userDto,url);
        if(!register){
            log.error("Error : {}","Register Failed");
            return CommonUtil.createBuildResponseMessage("Registration Failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("AuthController : verifyUserAccount() : Execution End");
        return CommonUtil.createBuildResponse("Register Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) throws Exception{
        LoginResponse loginResponse =  authService.login(loginRequest);
        if (ObjectUtils.isEmpty(loginResponse)){
            return CommonUtil.createErrorResponseMessage("Invalid credential",HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuildResponse(loginResponse,HttpStatus.OK);
    }

}
