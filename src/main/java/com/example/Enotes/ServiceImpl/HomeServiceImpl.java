package com.example.Enotes.ServiceImpl;

import com.example.Enotes.entity.AccountStatus;
import com.example.Enotes.entity.User;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.repository.UserRepository;
import com.example.Enotes.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("Invalid User"));

        if(user.getStatus().getVerificationCode()==null){
            throw new SuccessException("Account already verified");
        }

        if (user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);
//            user.setStatus(status); //not required as status referenced to same object
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
