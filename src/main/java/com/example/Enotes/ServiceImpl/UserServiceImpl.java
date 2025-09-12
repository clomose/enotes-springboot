package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.PasswordChangeRequest;
import com.example.Enotes.entity.User;
import com.example.Enotes.repository.UserRepository;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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
}
