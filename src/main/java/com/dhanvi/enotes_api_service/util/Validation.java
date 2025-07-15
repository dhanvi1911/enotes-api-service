package com.dhanvi.enotes_api_service.util;

import com.dhanvi.enotes_api_service.dto.UserDto;

import com.dhanvi.enotes_api_service.repository.RoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class Validation {

    @Autowired
    RoleRepo userRoleRepo;

    public void UserValidation(UserDto userDto){
        if(!StringUtils.hasText(userDto.getFirstName())){
            throw new IllegalArgumentException("First Name cannot be empty");
        }
        if(!StringUtils.hasText(userDto.getLastName())){
            throw new IllegalArgumentException("Last Name cannot be empty");
        }
        if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)){
            throw new IllegalArgumentException("Email is invalid");
        }
        if(!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOB_REGEX)){
            throw new IllegalArgumentException("Mobile number is invalid");
        }
        if(CollectionUtils.isEmpty(userDto.getRole())){
            throw new IllegalArgumentException("Invalid UserRole");
        }
        else {
            List<Integer> roleIDs= userRoleRepo.findAll().stream().map(r-> r.getId()).toList();
            System.out.println(roleIDs);
            List<Integer> reqRoleIds= userDto.getRole().stream()
                    .map(role->role.getId())
                    .filter(roleID -> !roleIDs.contains(roleID))
                    .toList();
            System.out.println(reqRoleIds);
            if (!CollectionUtils.isEmpty(reqRoleIds)){
                throw new IllegalArgumentException("role is invalid"+ roleIDs);
            }


        }




    }
}
