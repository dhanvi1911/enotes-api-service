package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.dto.EmailRequest;
import com.dhanvi.enotes_api_service.dto.UserDto;
import com.dhanvi.enotes_api_service.model.User;
import com.dhanvi.enotes_api_service.repository.RoleRepo;
import com.dhanvi.enotes_api_service.repository.UserRepo;

import com.dhanvi.enotes_api_service.service.UserService;
import com.dhanvi.enotes_api_service.util.Validation;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo userRoleRepo;

    @Autowired
    EmailService emailService;

    @Override
    public Boolean register(UserDto userDto) throws Exception {

        validation.UserValidation(userDto);
        User user = mapper.map(userDto, User.class);
        User saved =userRepo.save(user);
        if(!ObjectUtils.isEmpty(saved)){
            emailSend(saved);
            return true;
        }
        return false;
    }

    private void emailSend(User saved) throws Exception {
        String message = "Hi <b>"+saved.getFirstName()+"</b> <br>"
                +"Your account has been registered successfully <br>"
                +"Click on the below link to verify your account <br>"
                +"<a href = '#'>Click here</a><br>" +
                "Thanks <br> Enotes.com";
        EmailRequest emailRequest = EmailRequest.builder()
                .to(saved.getEmail())
                .title("Account creating confirmation")
                .subject("Account created successfully")
                .message(message)
                .build();
        emailService.SendEmail(emailRequest);

    }
}
