package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.FileDetails;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws Exception;
    public List<NotesDto> getAllNotes();

    byte[] downloadNote(Integer id) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;

    Page<NotesDto> getAllNotesByUserID(Integer UserId, int page, int size);

    void softDeleteNote(Integer id) throws Exception;

    void restoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBinNotes(Integer userID);

    void hardDeleteNote(Integer id) throws Exception;

    void deleteAllNotesFromRecycleBin(Integer userID) throws Exception;
}
