package com.example.Enotes.controller;

import com.example.Enotes.service.HomeService;
import com.example.Enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code) throws Exception{
        Boolean verify = homeService.verifyAccount(uid,code);
        if(verify){
            return CommonUtil.createBuildResponseMessage("Account verified successfully", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid Verification link",HttpStatus.BAD_REQUEST);
    }
}
