package com.dhanvi.enotes_api_service.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoDto {

    private Integer id;
    private String title;
    private StatusDto status;
    private Date createdOn;
    private Integer createdBy;
    private Integer updatedBy;
    private Date updatedOn;

}
