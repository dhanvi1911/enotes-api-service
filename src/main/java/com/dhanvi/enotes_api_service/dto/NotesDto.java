package com.dhanvi.enotes_api_service.dto;

import com.dhanvi.enotes_api_service.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotesDto {

    private Integer id;
    private String title;
    private String description;
    private CategoryDto category;
    private Date createdOn;
    private Integer createdBy;
    private Integer updatedBy;
    private Date updatedOn;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class CategoryDto{
        private Integer id;
        private String name;
    }

}
