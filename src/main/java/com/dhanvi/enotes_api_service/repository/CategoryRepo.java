package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
