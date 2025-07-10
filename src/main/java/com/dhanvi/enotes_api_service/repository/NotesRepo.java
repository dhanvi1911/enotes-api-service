package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepo extends JpaRepository<Notes, Integer> {
    List<Notes> findByCreatedBy(Integer userId);
    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userID);
    Page<Notes> findByIsDeletedFalse(Pageable pageable);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);

//    List<Notes> findByIdAndIsFavourite(Integer userID, boolean b);

    List<Notes> findByCreatedByAndIsFavourite(Integer userID, boolean b);
}
