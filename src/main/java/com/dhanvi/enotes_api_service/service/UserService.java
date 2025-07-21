package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.dto.LoginRequest;
import com.dhanvi.enotes_api_service.dto.LoginResponse;
import com.dhanvi.enotes_api_service.dto.UserDto;

public interface UserService {
    Boolean register(UserDto userDto) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
