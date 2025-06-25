package com.dhanvi.enotes_api_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Base {
    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false, nullable = false)
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;
}
