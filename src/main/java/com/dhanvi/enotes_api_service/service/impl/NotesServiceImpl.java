package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.NotesDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.model.FileDetails;
import com.dhanvi.enotes_api_service.model.Notes;
import com.dhanvi.enotes_api_service.repository.CategoryRepo;
import com.dhanvi.enotes_api_service.repository.FileDetailsRepo;
import com.dhanvi.enotes_api_service.repository.NotesRepo;
import com.dhanvi.enotes_api_service.service.NotesService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
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

//        checkCategoryExists(notesDto.getCategoryId());

        Category category = categoryRepo.findById(notesDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Invalid category ID"));

        Notes notes=mapper.map(notesDto, Notes.class);
        notes.setCategory(category);

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

        Notes saved=notesRepo.save(notes);
        if(!ObjectUtils.isEmpty(saved)){
            return true;
        }
        return false;
    }

    private String displayFileName(String originalFileName) {

       String extension= FilenameUtils.getExtension(originalFileName);
       String FileName= FilenameUtils.removeExtension(originalFileName);
       if(FileName.length()>8){
           FileName=FileName.substring(0,7);
       }
       FileName=FileName+"."+extension;
       return FileName;
    }


//    private void checkCategoryExists(Integer id) throws Exception {
//        categoryRepo.findById(categoryDto.getId()).orElseThrow(()-> new ResourceNotFoundExceptionHandler("Category ID inavlid"));
//
//    }

    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepo.findAll().stream().map(notes -> mapper.map(notes,NotesDto.class)).toList();
    }
}
