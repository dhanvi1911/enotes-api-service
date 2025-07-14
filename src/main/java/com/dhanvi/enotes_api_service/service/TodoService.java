package com.dhanvi.enotes_api_service.service;

import com.dhanvi.enotes_api_service.dto.TodoDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.Todo;

import java.util.List;

public interface TodoService {
    Boolean saveTodo(TodoDto todoDto);

    TodoDto getTodoByID(Integer id) throws Exception;

    List<TodoDto> getAllTodos();
}
