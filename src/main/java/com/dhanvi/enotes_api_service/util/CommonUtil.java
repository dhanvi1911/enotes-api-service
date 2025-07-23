package com.dhanvi.enotes_api_service.util;

import com.dhanvi.enotes_api_service.config.security.CustomUserDetails;
import com.dhanvi.enotes_api_service.handler.GenericResponse;
import com.dhanvi.enotes_api_service.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .data(data)
                .status(status)
                .message("Success")
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .status(status)
                .message(message)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .status(status)
                .message(message)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .data(data)
                .status(status)
                .message("Failed")
                .build();
        return response.create();
    }

    public static User getLoggedInUser() {
        try{
            CustomUserDetails loggedUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return loggedUser.getUser();
        } catch (Exception e) {
            throw e;
        }

    }
}
