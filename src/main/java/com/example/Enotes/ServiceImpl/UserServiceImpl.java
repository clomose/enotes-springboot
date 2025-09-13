package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.EmailRequest;
import com.example.Enotes.dto.PasswordChangeRequest;
import com.example.Enotes.dto.PswdResetRequest;
import com.example.Enotes.entity.User;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.repository.UserRepository;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User user = CommonUtil.getLoggedInUser();
        if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(),user.getPassword())){
            throw new IllegalArgumentException("Your old password is incorrect");
        }
        String encodePassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
        User user = userRepository.findByEmail(email);
        if (ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundException("Invalid Email");
        }

        //Generate unique password reset token
        String token = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(token);
        User updateUser = userRepository.save(user);

        String url = CommonUtil.getUrl(request);
        sendEmailRequest(updateUser,url);

    }

    @Override
    public void verifyPswdResetLink(Integer uid, String code) throws  Exception{
        User user = userRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Invalid user id"));
        verifyPasswordResetCode(user.getStatus().getPasswordResetToken(),code);

    }

    @Override
    public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception {
        User user = userRepository.findById(pswdResetRequest.getUid()).orElseThrow(() -> new ResourceNotFoundException("Invalid user id"));
        String encodedPassword = passwordEncoder.encode(pswdResetRequest.getNewPassword());
        user.setPassword(encodedPassword);
        user.getStatus().setVerificationCode(null);
        userRepository.save(user);
    }

    private void verifyPasswordResetCode(String existToken, String reqToken) {
        //reqToken not null
        if(StringUtils.hasText(reqToken)){

            //if user again hit the link after password reset
            if(!StringUtils.hasText(existToken)){
                throw new IllegalArgumentException("Already password reset");
            }

            if(!existToken.equals(reqToken)){
                throw new IllegalArgumentException("Invalid url");
            }
        }else{
            throw new IllegalArgumentException("Invalid token");
        }
    }

    private void sendEmailRequest(User user,String url) throws Exception {
        String message="Hi,</b>[[username]]<br> You have requested to reset your password <br>"
                +"<br> Click the below link to change the password <br>"
                +"<a href='[[url]]'>Change my password</a> <br><br>"
                +"Thanks,<br> Notes.com";

        message = message.replace("[[username]]",user.getFirstName());
        message = message.replace("[[url]]",url + "/api/v1/home/verify-pswd-link?uid="+user.getId()+"&&code="+user.getStatus().getPasswordResetToken());
        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .title("Password Reset")
                .subject("Password Reset Link")
                .message(message)
                .build();
        emailService.sendEmail(emailRequest);
    }
}
