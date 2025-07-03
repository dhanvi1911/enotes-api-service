package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.Notes;
import com.dhanvi.enotes_api_service.repository.CategoryRepo;
import com.dhanvi.enotes_api_service.repository.NotesRepo;
import com.dhanvi.enotes_api_service.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepo notesRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception {

        checkCategoryExists(notesDto.getCategory());

        Notes notes=mapper.map(notesDto, Notes.class);
        

        Notes saved=notesRepo.save(notes);
        if(!ObjectUtils.isEmpty(saved)){
            return true;
        }
        return false;
    }

    private void checkCategoryExists(NotesDto.CategoryDto categoryDto) throws Exception {
        categoryRepo.findById(categoryDto.getId()).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Category ID inavlid"));
        
    }

    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepo.findAll().stream().map(notes -> mapper.map(notes,NotesDto.class)).toList();
    }
}
