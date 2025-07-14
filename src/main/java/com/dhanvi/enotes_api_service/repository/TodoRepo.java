package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<Todo,Integer> {

}
