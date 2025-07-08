package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepo extends JpaRepository<Notes, Integer> {
    List<Notes> findByCreatedBy(Integer userId);
}
