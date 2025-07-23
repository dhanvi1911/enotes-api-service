package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.PasswordChangeRequest;
import com.dhanvi.enotes_api_service.dto.UserResponse;
import com.dhanvi.enotes_api_service.model.User;
import com.dhanvi.enotes_api_service.service.UserService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("profile")
    public ResponseEntity<?> getProfile(){
        User loggedInUser = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);

    }

    @PostMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
        userService.changePassword(passwordChangeRequest);
        return CommonUtil.createBuildResponseMessage("Successfully changed the password",HttpStatus.OK );

    }
}
