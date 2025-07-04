package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.service.NotesService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;


    @PostMapping("save")
    public ResponseEntity<?> saveNotes(@ModelAttribute NotesDto notesDto) throws Exception {
        Boolean savedNotes= notesService.saveNotes(notesDto);
        if(savedNotes){
            return CommonUtil.createBuildResponseMessage("Note saved successfully", HttpStatus.CREATED);
        }
        else {
            return CommonUtil.createErrorResponseMessage("Could not Save notes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @InitBinder
    public void bindCategory(WebDataBinder binder) {
        binder.registerCustomEditor(CategoryDto.class, "category", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                System.out.println("Converting category = " + text); // Debugging line
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId(Integer.parseInt(text));
                setValue(categoryDto);
            }
        });
    }



    @GetMapping("get-notes")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notesDtos= notesService.getAllNotes();
        if (CollectionUtils.isEmpty(notesDtos)){
            return ResponseEntity.noContent().build();
        }
        else return CommonUtil.createBuildResponse(notesDtos, HttpStatus.OK);
    }
}
