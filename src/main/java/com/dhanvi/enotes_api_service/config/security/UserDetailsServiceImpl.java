package com.dhanvi.enotes_api_service.config.security;

import com.dhanvi.enotes_api_service.model.User;
import com.dhanvi.enotes_api_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepo.findByEmail(username);
      if (user==null){
          throw new UsernameNotFoundException("Invalid username");
      }
        return new CustomUserDetails(user);
    }
}
