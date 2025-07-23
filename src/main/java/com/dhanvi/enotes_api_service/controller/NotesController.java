package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.FileDetails;
import com.dhanvi.enotes_api_service.model.Notes;
import com.dhanvi.enotes_api_service.service.NotesService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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

    @GetMapping("user-notes")   // for pagination
    public ResponseEntity<?> getAllNotesOfUser(@RequestParam (defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Integer userID = CommonUtil.getLoggedInUser().getId();
        Page<NotesDto> notesDtos = notesService.getAllNotesByUserID(userID, page, size);
        return new ResponseEntity<>(notesDtos, HttpStatus.FOUND);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws Exception {
        notesService.softDeleteNote(id);
        return CommonUtil.createBuildResponseMessage("Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Restored the note", HttpStatus.OK);
    }

    @GetMapping("RecycleBinNotes")
    public ResponseEntity<?> getUserRecycleBinNotes(){
        Integer userID = CommonUtil.getLoggedInUser().getId();
        List<NotesDto> RecycledNotes = notesService.getUserRecycleBinNotes(userID);
        if (CollectionUtils.isEmpty(RecycledNotes)){
            return CommonUtil.createBuildResponseMessage("No notes present in the recycled bin", HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(RecycledNotes, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> hardDeleteNote(@PathVariable Integer id) throws Exception {
        notesService.hardDeleteNote(id);
        return CommonUtil.createBuildResponseMessage("Deleted the note from DB", HttpStatus.OK);
    }

    @DeleteMapping("empty-recycle-bin")
    public ResponseEntity<?> deleteAllNotesFromRecycleBin() throws Exception {
        Integer userID=CommonUtil.getLoggedInUser().getId();
        notesService.deleteAllNotesFromRecycleBin(userID);
        return CommonUtil.createBuildResponseMessage("Deleted Notes from Recycle bin for the user", HttpStatus.OK);
    }

    @GetMapping("markfav/{noteID}")
    public ResponseEntity<?> markFavouriteNote(@PathVariable Integer noteID) throws Exception {
        notesService.markFavouriteNote(noteID);
        return CommonUtil.createBuildResponseMessage("Marked the note as Favourite",HttpStatus.OK);
    }

    @GetMapping("unmarkfav/{noteID}")
    public ResponseEntity<?> unmarkFavourite(@PathVariable Integer noteID) throws Exception {
        notesService.unmarkFavourite(noteID);
        return CommonUtil.createBuildResponseMessage("Unmarked the notes from Favourite", HttpStatus.OK);
    }

    @GetMapping("Favourites")
    public ResponseEntity<?> allFavouritesNotes(){
        Integer userID = CommonUtil.getLoggedInUser().getId();
        List<Notes> notes = notesService.allFavouriteNotes(userID);
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @GetMapping("copy-note/{id}")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception {
        notesService.copyNotes(id);
        return CommonUtil.createBuildResponseMessage("Copied Successfully", HttpStatus.CREATED);
    }



}
