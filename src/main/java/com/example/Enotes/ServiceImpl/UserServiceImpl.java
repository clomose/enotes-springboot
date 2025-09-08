package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.EmailRequest;
import com.example.Enotes.dto.UserDto;
import com.example.Enotes.entity.Role;
import com.example.Enotes.entity.User;
import com.example.Enotes.repository.RoleRepository;
import com.example.Enotes.repository.UserRepository;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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

    @Override
    public Boolean register(UserDto userDto) throws Exception{
        validation.userValidation(userDto);

        User user = mapper.map(userDto,User.class);
        setRole(userDto,user);
        //very important concept
        //We did it because we need actual role objects from the database
        User save = userRepository.save(user);
        if(!ObjectUtils.isEmpty(save)){
            //mail
            emailSend(save);
            return true;
        }
        return false;
    }

    private void emailSend(User save) throws Exception {
        String message= "Hi,<b>"+save.getFirstName()
                +"</b> <br> Your account register successfully <br>"
                +"<br> Click the below link and verify your account <br>"
                +"<a href='#'>Click Here</a> <br><br>"
                +"Thanks,<br> Notes.com";
        EmailRequest emailRequest = EmailRequest.builder()
                .to(save.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> roleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepository.findAllById(roleId);
        user.setRoles(roles);
    }
}
