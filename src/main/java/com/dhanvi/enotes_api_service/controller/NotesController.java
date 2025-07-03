package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.service.NotesService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("save")
    public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws Exception {
        Boolean savedNotes= notesService.saveNotes(notesDto);
        if(savedNotes){
            return CommonUtil.createBuildResponseMessage("Note saved successfully", HttpStatus.CREATED);
        }
        else {
            return CommonUtil.createErrorResponseMessage("COuld not Save notes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
