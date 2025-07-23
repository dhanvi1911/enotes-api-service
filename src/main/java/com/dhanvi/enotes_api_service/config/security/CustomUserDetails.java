package com.dhanvi.enotes_api_service.config.security;

import com.dhanvi.enotes_api_service.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List <SimpleGrantedAuthority> authority = new ArrayList<>();
        user.getRole().forEach(r->{
            authority.add(new SimpleGrantedAuthority("ROLE_" +r.getName().toUpperCase()));
        });
        return authority;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
