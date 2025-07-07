package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.model.FileDetails;
import com.dhanvi.enotes_api_service.service.NotesService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

   @GetMapping("download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
        byte [] downloadedFile = notesService.downloadNote(id);
       FileDetails fileDetails= notesService.getFileDetails(id);
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       headers.setContentDisposition(ContentDisposition
               .attachment()
               .filename(fileDetails.getDisplayFileName())
               .build());
       return new ResponseEntity<>(downloadedFile, headers, HttpStatus.OK);
       

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
