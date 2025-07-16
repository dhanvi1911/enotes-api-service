package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.AccountStatus;
import com.dhanvi.enotes_api_service.model.User;
import com.dhanvi.enotes_api_service.repository.UserRepo;
import com.dhanvi.enotes_api_service.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    UserRepo userRepo;

    @Override
    public Boolean verifyUserAccount(Integer id, String code) throws Exception {
        User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid user ID"));

        if(user.getAccountStatus().getVerificationCode()==null){
            throw new Exception("Account already verified");
        }

        if(user.getAccountStatus().getVerificationCode().equals(code)){
            AccountStatus accountStatus = user.getAccountStatus();
            accountStatus.setIsActive(true);
            accountStatus.setVerificationCode(null);
            userRepo.save(user);
            return true;
        }
        return false;
    }
}
