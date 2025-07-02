package com.dhanvi.enotes_api_service.handler;
import com.dhanvi.enotes_api_service.dto.CategoryDto;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenericResponse {

    private HttpStatus status;
    private String message;
    private Object data;

    public ResponseEntity<?> create(){
        Map<String, Object> response=new LinkedHashMap<>();
        response.put("status",status);
        response.put("message",message);
        if(!ObjectUtils.isEmpty(data)){
            response.put("data",data);
        }
        return new ResponseEntity<>(response,status);
    }

}
