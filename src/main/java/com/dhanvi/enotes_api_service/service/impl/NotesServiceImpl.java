package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.FileDetailsDto;
import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.model.FileDetails;
import com.dhanvi.enotes_api_service.model.Notes;
import com.dhanvi.enotes_api_service.repository.CategoryRepo;
import com.dhanvi.enotes_api_service.repository.FileDetailsRepo;
import com.dhanvi.enotes_api_service.repository.NotesRepo;
import com.dhanvi.enotes_api_service.service.NotesService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepo notesRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileDetailsRepo fileDetailsRepo;


    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception {

        checkCategoryExists(notesDto.getCategory());
        Notes notes;

        if (!ObjectUtils.isEmpty(notesDto.getId())){
            notes = notesRepo.findById(notesDto.getId()).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid Notes ID"));
//           updateCategory(notesDto);
        }
        else {
            notes = new Notes();
        }
        CategoryDto categoryDto = notesDto.getCategory();
//        notes=mapper.map(notesDto, Notes.class);
        notes.setTitle(notesDto.getTitle());
        notes.setDescription(notesDto.getDescription());
        notes.setCategory(mapper.map(categoryDto, Category.class));

        MultipartFile file = notesDto.getFileDetails();
        if(file !=null && !file.isEmpty()){
            FileDetails fileDetails =new FileDetails();

            String originalFileName = file.getOriginalFilename();
            String displayFileName = displayFileName(originalFileName);

            fileDetails.setOriginalFileName(originalFileName);
            fileDetails.setDisplayFileName(displayFileName);
            fileDetails.setFileSize(file.getSize());

            String UniqueFileName= UUID.randomUUID().toString();
            String extension= FilenameUtils.getExtension(originalFileName);
            String UploadFilename = UniqueFileName+"."+extension;

            fileDetails.setUploadFileName(UploadFilename);

            File saveFile=new File(uploadPath);
            if(!saveFile.exists()){
                saveFile.mkdirs();
            }

            String storePath = uploadPath.concat(UploadFilename);
            fileDetails.setPath(storePath);
            Files.copy(file.getInputStream(), Paths.get(storePath));

            FileDetails fileDetail = FileDetails.builder()
                    .uploadFileName(UploadFilename)
                    .originalFileName(originalFileName)
                    .displayFileName(displayFileName) // or customize
                    .path(storePath)
                    .fileSize(file.getSize())
                    .build();

            FileDetails savedFile = fileDetailsRepo.save(fileDetail);
            notes.setFileDetails(savedFile);
        }
        else if(!ObjectUtils.isEmpty(notesDto.getId())){
            Notes exsistingNote = notesRepo.findById(notesDto.getId()).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Inavlid notes ID"));
            notes.setFileDetails(exsistingNote.getFileDetails());
        }

        Notes saved=notesRepo.save(notes);
        if(!ObjectUtils.isEmpty(saved)){
            return true;
        }
        return false;
    }

//    private void updateCategory(NotesDto notesDto) throws Exception {
//
//        Notes notesExists = notesRepo.findById(notesDto.getId()).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Inavlid Categpry ID"));
//        if(notesDto.getFileDetails()==null && notesDto.getFileDetails().isEmpty()){
//            notesExists.setFileDetails(notesExists.getFileDetails());
//        }
//        notesExists.setDescription(notesDto.getDescription());
//        notesExists.setTitle(notesDto.getDescription());
//
//        if(ObjectUtils.isEmpty(notesDto.getCategory())){
//            notesExists.setCategory(notesExists.getCategory());
//        }
//
//
//    }

    private String displayFileName(String originalFileName) {

       String extension= FilenameUtils.getExtension(originalFileName);
       String FileName= FilenameUtils.removeExtension(originalFileName);
       if(FileName.length()>8){
           FileName=FileName.substring(0,7);
       }
       FileName=FileName+"."+extension;
       return FileName;
    }


    private void checkCategoryExists(CategoryDto categoryDto) throws Exception {
        categoryRepo.findById(categoryDto.getId()).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Category ID inavlid"));
//

//        Category category = categoryRepo.findById(notesDto.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Invalid category ID"));



    }

//    @Override
//    public List<NotesDto> getAllNotes() {
//
//        return notesRepo.findAll().stream().map(notes -> mapper.map(notes,NotesDto.class)).toList();
//    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepo.findAll().stream().map(notes -> {
            NotesDto dto = mapper.map(notes, NotesDto.class);

//            //  Manually map file details
//            if (notes.getFileDetails() != null) {
//                FileDetailsDto fileDto = mapper.map(notes.getFileDetails(), FileDetailsDto.class);
//                dto.setFileDetailsDto(fileDto);
//            }

            return dto;
        }).toList();
    }

    @Override
    public byte[] downloadNote(Integer id) throws Exception {
        FileDetails fileDetails = fileDetailsRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptionHandler("File is not available"));
        InputStream io= new FileInputStream(fileDetails.getPath());
        byte [] byteData = StreamUtils.copyToByteArray(io);
        return byteData;
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
        FileDetails fileDetails = fileDetailsRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptionHandler("File is not available"));
        return fileDetails;
    }

    @Override
    public Page<NotesDto> getAllNotesByUserID(Integer UserId, int page, int size) {
//        List<Notes> notes =notesRepo.findByCreatedBy(UserId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Notes> notesPage = notesRepo.findByIsDeletedFalse(pageable);
        return notesPage.map(note -> mapper.map(note, NotesDto.class));

    }

    @Override
    public void softDeleteNote(Integer id) throws Exception {
        Notes note = notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid Notes ID to delete"));
        note.setDeleted(true);
        note.setDeletedOn(LocalDateTime.now());
        notesRepo.save(note);
    }

    @Override
    public void restoreNotes(Integer id) throws Exception {
        Notes note= notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Note not found with this id"));
        note.setDeleted(false);
        note.setDeletedOn(null);
        notesRepo.save(note);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer userID) {
        List<Notes> RecycledNotes= notesRepo.findByCreatedByAndIsDeletedTrue(userID);
       List<NotesDto> notesDtoList= RecycledNotes.stream().map(notes -> mapper.map(notes, NotesDto.class)).toList();
        return notesDtoList;
    }

    @Override
    public void hardDeleteNote(Integer id) throws Exception {
       Notes note= notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid ID to delete"));
       if(note.isDeleted()){
           notesRepo.delete(note);
       }
       else {
           throw new IllegalArgumentException("Notes cannot be directly hard deleted");
       }

    }

    @Override
    public void deleteAllNotesFromRecycleBin(Integer userID) throws Exception {
        List<Notes> deletedNotes= notesRepo.findByCreatedByAndIsDeletedTrue(userID);
        if(!CollectionUtils.isEmpty(deletedNotes))
        {
            notesRepo.deleteAll(deletedNotes);
        }

    }

    @Override
    public void markFavouriteNote(Integer noteID) throws Exception {
       Notes note = notesRepo.findById(noteID).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid Note ID"));
       if(note.getIsFavourite()){
           throw new IllegalStateException("Note already marked as favourite");
       }
       else{
           note.setIsFavourite(true);
           notesRepo.save(note);
       }

    }

    @Override
    public void unmarkFavourite(Integer noteID) throws Exception {
        Notes note =notesRepo.findById(noteID).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid Note ID"));
        if(note.getIsFavourite()){
            note.setIsFavourite(false);
            notesRepo.save(note);
        }
        else{
            throw new IllegalStateException("Note has note been marked as favourite");
        }

    }

    @Override
    public List<Notes> allFavouriteNotes(Integer userID) {
        List<Notes> Favnotes = notesRepo.findByCreatedByAndIsFavourite(userID, true);
        return Favnotes;
    }

    @Override
    public void copyNotes(Integer id) throws Exception {
        Notes note =notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Invalid Notes ID"));
        Notes newNote = new Notes();
        newNote.setTitle(note.getTitle());
        newNote.setCategory(note.getCategory());
        newNote.setDescription(note.getDescription());
        newNote.setIsFavourite(false);
        notesRepo.save(newNote);
    }


}
