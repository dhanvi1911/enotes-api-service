package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.service.HomeService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount (@RequestParam Integer id, @RequestParam String code) throws Exception {
        Boolean verifiedAccount =homeService.verifyUserAccount(id,code);
        if(verifiedAccount){
            return CommonUtil.createBuildResponseMessage("User Verified", HttpStatus.FOUND);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.NOT_FOUND);
    }
}
