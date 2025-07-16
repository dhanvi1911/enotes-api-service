package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;

public interface HomeService {
    Boolean verifyUserAccount(Integer id, String code) throws Exception;
}
