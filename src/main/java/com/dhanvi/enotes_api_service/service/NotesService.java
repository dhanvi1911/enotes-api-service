package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.dto.NotesDto;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws Exception;
    public List<NotesDto> getAllNotes();
}
