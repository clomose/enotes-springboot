package com.example.Enotes.config;

import com.example.Enotes.entity.User;
import com.example.Enotes.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        User loggedInUser = CommonUtil.getLoggedInUser();
        return Optional.of(loggedInUser.getId());
    }
}
