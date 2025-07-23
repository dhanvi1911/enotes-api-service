package com.dhanvi.enotes_api_service.dto;

import com.dhanvi.enotes_api_service.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNo;
    private StatusDto status;
    private List<Role> role;
}
