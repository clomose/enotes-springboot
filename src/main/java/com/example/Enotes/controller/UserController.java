package com.example.Enotes.controller;

import com.example.Enotes.dto.PasswordChangeRequest;
import com.example.Enotes.dto.UserResponse;
import com.example.Enotes.entity.User;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        User loggedInUser =  CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
        userService.changePassword(passwordChangeRequest);
        return CommonUtil.createBuildResponseMessage("Password Changed successfully",HttpStatus.OK);
    }

}
