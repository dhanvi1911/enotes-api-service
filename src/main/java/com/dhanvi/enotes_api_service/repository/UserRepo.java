package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);

    User findByEmail(String username);
}
