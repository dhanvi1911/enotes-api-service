package com.dhanvi.enotes_api_service.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class FileDetailsDto {

        private Integer id;
        private String uploadFileName;
        private String originalFilename;
        private String displayFileName;
        private String path;
        private Long fileSize;

    }


