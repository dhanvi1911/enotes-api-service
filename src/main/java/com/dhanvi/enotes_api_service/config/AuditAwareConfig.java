package com.dhanvi.enotes_api_service.config;

import com.dhanvi.enotes_api_service.model.User;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {

        User user =CommonUtil.getLoggedInUser();
        return Optional.of(user.getId());
    }
}
