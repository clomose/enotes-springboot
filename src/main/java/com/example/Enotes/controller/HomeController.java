package com.example.Enotes.controller;

import com.example.Enotes.dto.PswdResetRequest;
import com.example.Enotes.endpoints.HomeEndpoint;
import com.example.Enotes.service.HomeService;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController implements HomeEndpoint {

    //logger object for HomeController class
    Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code) throws Exception{
        log.info("HomeController : verifyUserAccount() : Execution Start");
        Boolean verify = homeService.verifyAccount(uid,code);
        if(verify){
            return CommonUtil.createBuildResponseMessage("Account verified successfully", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid Verification link",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception{
        userService.sendEmailPasswordReset(email,request);
        return CommonUtil.createBuildResponseMessage("Email Send Success!! Check Mail",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception{
        userService.verifyPswdResetLink(uid,code);
        return CommonUtil.createBuildResponseMessage("verification success",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws  Exception{
        userService.resetPassword(pswdResetRequest);
        return CommonUtil.createBuildResponseMessage("Password reset successfully",HttpStatus.OK);
    }
}
