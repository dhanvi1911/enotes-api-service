package com.dhanvi.enotes_api_service.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private Integer uid;
    private String newPassword;
}
