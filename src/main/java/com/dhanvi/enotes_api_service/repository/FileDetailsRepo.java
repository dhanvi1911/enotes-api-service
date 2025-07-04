package com.dhanvi.enotes_api_service.repository;

import com.dhanvi.enotes_api_service.model.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepo extends JpaRepository<FileDetails, Integer> {

}
