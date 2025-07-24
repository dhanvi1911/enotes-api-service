package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.dto.*;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;

public interface UserService {
    Boolean register(UserDto userDto) throws Exception;

    LoginResponse login(LoginRequest loginRequest);

    void changePassword(PasswordChangeRequest passwordChangeRequest);

    void sendEmailPasswordReset(String Email) throws Exception;

    void verifyPasswordResetLink(Integer uid, String code) throws Exception;

    void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception;
}
