package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String GenerateToken(User user);

    public String extractUsername(String token);

    public Boolean validateToken(String token, UserDetails userDetails);
}
