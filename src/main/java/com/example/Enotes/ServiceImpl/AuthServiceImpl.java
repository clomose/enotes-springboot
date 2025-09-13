package com.example.Enotes.ServiceImpl;

import com.example.Enotes.config.security.CustomUserDetails;
import com.example.Enotes.dto.EmailRequest;
import com.example.Enotes.dto.LoginRequest;
import com.example.Enotes.dto.UserRequest;
import com.example.Enotes.dto.UserResponse;
import com.example.Enotes.entity.AccountStatus;
import com.example.Enotes.entity.Role;
import com.example.Enotes.entity.User;
import com.example.Enotes.handler.LoginResponse;
import com.example.Enotes.repository.RoleRepository;
import com.example.Enotes.repository.UserRepository;
import com.example.Enotes.service.JwtService;
import com.example.Enotes.service.AuthService;
import com.example.Enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public Boolean register(UserRequest userDto, String url) throws Exception{
        validation.userValidation(userDto);

        User user = mapper.map(userDto,User.class);
        setRole(userDto,user);
        //very important concept
        //We did it because we need actual role objects from the database

        AccountStatus status = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        if(!ObjectUtils.isEmpty(save)){
            //mail
            emailSendForRegister(save,url);
            return true;
        }
        return false;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        //First we have to check if the user is verified or not


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        if (authentication.isAuthenticated()){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(customUserDetails.getUser());
            LoginResponse loginResponse = LoginResponse.builder()
                    .token(token)
                    .user(mapper.map(customUserDetails.getUser(), UserResponse.class))
                    .build();
            return loginResponse;
        }
        return null;
    }

    private void emailSendForRegister(User save,String url) throws Exception {
        String message="Hi,</b>[[username]]<br> Your account register successfully <br>"
                +"<br> Click the below link and verify your account <br>"
                +"<a href='[[url]]'>Click Here</a> <br><br>"
                +"Thanks,<br> Notes.com";

        message = message.replace("[[username]]",save.getFirstName());
        message = message.replace("[[url]]",url + "/api/v1/home/verify?uid="+save.getId()+"&&code="+save.getStatus().getVerificationCode());
        EmailRequest emailRequest = EmailRequest.builder()
                .to(save.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserRequest userDto, User user) {
        List<Integer> roleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepository.findAllById(roleId);
        user.setRoles(roles);
    }
}
