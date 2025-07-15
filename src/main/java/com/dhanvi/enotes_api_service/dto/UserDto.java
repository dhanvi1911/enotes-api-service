package com.dhanvi.enotes_api_service.dto;

import com.dhanvi.enotes_api_service.model.Role;
import com.dhanvi.enotes_api_service.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobNo;
    private List<Role> role;
}
