package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepo extends JpaRepository<Notes, Integer> {
    List<Notes> findByCreatedBy(Integer userId);
    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userID);
    Page<Notes> findByIsDeletedFalse(Pageable pageable);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);

//    List<Notes> findByIdAndIsFavourite(Integer userID, boolean b);

    List<Notes> findByCreatedByAndIsFavourite(Integer userID, boolean b);

    @Query("select n from Notes n where (lower(n.title) like lower(concat('%',:keyword,'%')) "
            + "or lower(n.category.name) like lower(concat('%',:keyword,'%')) "
            + "or lower(n.description) like lower(concat('%',:keyword,'%'))) "
            + "and n.isDeleted = false "
            + "and n.createdBy = :userID" )
    Page<Notes> searchNotes(@Param("keyword") String keyword, @Param("userID") Integer userID, Pageable pageable);

}
