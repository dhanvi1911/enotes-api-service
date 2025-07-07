package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.FileDetails;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws Exception;
    public List<NotesDto> getAllNotes();

    byte[] downloadNote(Integer id) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;
}
