package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.config.security.CustomUserDetails;
import com.dhanvi.enotes_api_service.dto.*;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.AccountStatus;
import com.dhanvi.enotes_api_service.model.User;
import com.dhanvi.enotes_api_service.repository.RoleRepo;
import com.dhanvi.enotes_api_service.repository.UserRepo;

import com.dhanvi.enotes_api_service.service.JwtService;
import com.dhanvi.enotes_api_service.service.UserService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import com.dhanvi.enotes_api_service.util.Validation;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.beans.Encoder;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo userRoleRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    PasswordEncoder passEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public Boolean register(UserDto userDto) throws Exception {

        validation.UserValidation(userDto);
        User user = mapper.map(userDto, User.class);


        AccountStatus accountStatus = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setAccountStatus(accountStatus);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved =userRepo.save(user);
        if(!ObjectUtils.isEmpty(saved)){
//            emailSend(saved);
            return true;
        }
        return false;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if (authenticate.isAuthenticated()){
            CustomUserDetails customUserDetails= (CustomUserDetails)authenticate.getPrincipal();
            String token = jwtService.GenerateToken(customUserDetails.getUser());
            LoginResponse loginResponse = LoginResponse.builder()
                    .userDto(mapper.map(customUserDetails.getUser(), UserResponse.class))
                    .token(token)
                    .build();
            return loginResponse;

        }
        return null;
    }

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
       User user = CommonUtil.getLoggedInUser();
       if(!passEncoder.matches(passwordChangeRequest.getOldPassword(),user.getPassword())){
           throw new IllegalArgumentException("Old Password is incorrect.");
       }
       String encodedPassword = passEncoder.encode(passwordChangeRequest.getNewPassword());
       user.setPassword(encodedPassword);
       userRepo.save(user);
    }

    @Override
    public void sendEmailPasswordReset(String Email) throws Exception {
        User user = userRepo.findByEmail(Email);
        if(ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundExceptionHandler("Invalid Email ID");
        }

        String passwordResetToken = UUID.randomUUID().toString();
        user.getAccountStatus().setPasswordResetToken(passwordResetToken);
        User updatedUser =userRepo.save(user);

        sendEmailRequest(updatedUser);
    }

    @Override
    public void verifyPasswordResetLink(Integer uid, String code) throws Exception {
        User user = userRepo.findById(uid).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid User"));
        String exsistToken = user.getAccountStatus().getPasswordResetToken();
        if(StringUtils.hasText(code)){
            if (!StringUtils.hasText(exsistToken)){
                throw new IllegalArgumentException("You have already resetted the password once");
            }
            if (!exsistToken.equalsIgnoreCase(code)){
                throw new IllegalArgumentException("invalid url");
            }

        }else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception{
        User user = userRepo.findById(passwordResetRequest.getUid()).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid User"));
        String encodedPass = passEncoder.encode(passwordResetRequest.getNewPassword());
        user.setPassword(encodedPass);
        user.getAccountStatus().setPasswordResetToken(null);
        userRepo.save(user);

    }

    private void sendEmailRequest(User user) throws Exception {
        String message = "Hi <b>"+user.getFirstName()+"</b> <br>"
                +"You have requested to reset the password of your account.<br>"
                +"Click on the below link to change the password. <br>"
                +"<a href ='[[url]]'>Change password</a><br>"
                +"<p> Ignore the mail if your remember the password or if you have not made the request.</p>"
                + "Thanks <br> Enotes.com";

        message = message.replace("[[url]]","http://localhost:8080/api/v1/home/verify-password-link?'uid="+user.getId()+"&&code="+user.getAccountStatus().getPasswordResetToken());
        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .title("Password Reset")
                .subject("Password Reset link")
                .message(message)
                .build();
        emailService.SendEmail(emailRequest);
    }

    private void emailSend(User saved) throws Exception {
        String message = "Hi <b>"+saved.getFirstName()+"</b> <br>"
                +"Your account has been registered successfully <br>"
                +"Click on the below link to verify your account <br>"
                +"<a href ='[[url]]'>Click here</a><br>"
                + "Thanks <br> Enotes.com";

        message = message.replace("[[url]]","http://localhost:8080/api/v1/home/verify?'uid="+saved.getId()+"&&code="+saved.getAccountStatus().getVerificationCode());
        EmailRequest emailRequest = EmailRequest.builder()
                .to(saved.getEmail())
                .title("Account creating confirmation")
                .subject("Account created successfully")
                .message(message)
                .build();
        emailService.SendEmail(emailRequest);

    }
}
