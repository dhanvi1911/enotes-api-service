package com.dhanvi.enotes_api_service.dto;

import lombok.Data;

@Data
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;
}
