package com.dhanvi.enotes_api_service.controller;
import com.dhanvi.enotes_api_service.dto.UserDto;
import com.dhanvi.enotes_api_service.service.UserService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("save")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws Exception {
        Boolean registered = userService.register(userDto);
        if(registered){
            return CommonUtil.createErrorResponseMessage("Registered SUccessfully", HttpStatus.CREATED);
        }
        else {
            return CommonUtil.createErrorResponseMessage("Could not register the user", HttpStatus.BAD_REQUEST);
        }
    }


}
