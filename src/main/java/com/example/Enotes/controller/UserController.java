package com.example.Enotes.controller;

import com.example.Enotes.dto.PasswordChangeRequest;
import com.example.Enotes.dto.UserResponse;
import com.example.Enotes.endpoints.UserEndpoint;
import com.example.Enotes.entity.User;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController implements UserEndpoint {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> getProfile(){
        User loggedInUser =  CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(PasswordChangeRequest passwordChangeRequest){
        userService.changePassword(passwordChangeRequest);
        return CommonUtil.createBuildResponseMessage("Password Changed successfully",HttpStatus.OK);
    }

}
