package com.dhanvi.enotes_api_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequest {
    private String to;
    private String message;
    private String subject;
    private String title;

}
