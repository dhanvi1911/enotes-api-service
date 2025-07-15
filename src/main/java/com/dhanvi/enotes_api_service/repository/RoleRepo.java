package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
