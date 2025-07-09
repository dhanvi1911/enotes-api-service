package com.dhanvi.enotes_api_service.schedular;

import com.dhanvi.enotes_api_service.model.Notes;
import com.dhanvi.enotes_api_service.repository.NotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {

    @Autowired
    private NotesRepo notesRepo;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesSchedular(){
       LocalDateTime cutOffDate= LocalDateTime.now().minusDays(7);
       List<Notes> DeletedNotes=notesRepo.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
       notesRepo.deleteAll(DeletedNotes);

    }


}
