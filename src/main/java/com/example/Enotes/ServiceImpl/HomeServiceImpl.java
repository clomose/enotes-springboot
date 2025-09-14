package com.example.Enotes.ServiceImpl;

import com.example.Enotes.entity.AccountStatus;
import com.example.Enotes.entity.User;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.exception.SuccessException;
import com.example.Enotes.repository.UserRepository;
import com.example.Enotes.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
        log.info("HomeServiceImpl : verifyAccount() : start");
        User user = userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("Invalid User"));

        if(user.getStatus().getVerificationCode()==null){
            log.info("Message : Account already verified");
            throw new SuccessException("Account already verified");
        }

        if (user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);
//            user.setStatus(status); //not required as status referenced to same object
            userRepository.save(user);
            log.info("message : Account verification success");
            return true;
        }
        log.info("HomeServiceImpl : verifyAccount() : end");
        return false;
    }
}
