package com.dhanvi.enotes_api_service.service.impl;

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

    @Override
    public Boolean register(UserDto userDto) {

        validation.UserValidation(userDto);
        User user = mapper.map(userDto, User.class);
        User saved =userRepo.save(user);
        if(!ObjectUtils.isEmpty(saved)){
            return true;
        }
        return false;
    }
}
