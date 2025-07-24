package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.PasswordResetRequest;
import com.dhanvi.enotes_api_service.service.HomeService;
import com.dhanvi.enotes_api_service.service.UserService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount (@RequestParam Integer id, @RequestParam String code) throws Exception {
        Boolean verifiedAccount =homeService.verifyUserAccount(id,code);
        if(verifiedAccount){
            return CommonUtil.createBuildResponseMessage("User Verified", HttpStatus.FOUND);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.NOT_FOUND);
    }

    @GetMapping("send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email) throws Exception {
        userService.sendEmailPasswordReset(email);
        return CommonUtil.createBuildResponseMessage("Email sent successfully, check your email for reset password link", HttpStatus.OK);
    }

    @GetMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception {
        userService.resetPassword(passwordResetRequest);
        return CommonUtil.createBuildResponseMessage("Password Reset SUccessfull",HttpStatus.OK);
    }

    @PostMapping("verify-password-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        userService.verifyPasswordResetLink(uid, code);
        return CommonUtil.createErrorResponseMessage("Verified Successfully", HttpStatus.OK);
    }

}
