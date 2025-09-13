package com.example.Enotes.service;

import com.example.Enotes.dto.PasswordChangeRequest;
import com.example.Enotes.dto.PswdResetRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
     public void changePassword(PasswordChangeRequest passwordChangeRequest);

     void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;

     void verifyPswdResetLink(Integer uid, String code) throws Exception;

     void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;
}
