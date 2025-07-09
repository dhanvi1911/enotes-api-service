package com.dhanvi.enotes_api_service.dto;

import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.model.FileDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotesDto {

    private Integer id;
    private String title;
    private String description;
//    private Integer categoryId;
    private CategoryDto category;
    private Date createdOn;
    private Integer createdBy;
    private Integer updatedBy;
    private Date updatedOn;
    private Boolean isDeleted;
    private LocalDateTime deletedOn;

    @JsonIgnore
    private MultipartFile fileDetails;
    private FileDetailsDto fileDetailsDto;

//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Setter
//    @Getter
//    public static class CategoryDto{
//        private Integer id;
//        private String name;
//    }

}
