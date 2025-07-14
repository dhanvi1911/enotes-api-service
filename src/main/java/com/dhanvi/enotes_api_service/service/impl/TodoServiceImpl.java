package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.dto.StatusDto;
import com.dhanvi.enotes_api_service.dto.TodoDto;
import com.dhanvi.enotes_api_service.enums.TodoStatus;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.Todo;
import com.dhanvi.enotes_api_service.repository.TodoRepo;
import com.dhanvi.enotes_api_service.service.TodoService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private ModelMapper mapper;


    @Override
    public Boolean saveTodo(TodoDto todoDto) {
        Todo todo = new Todo();
        if (todoDto.getStatus() != null) {
            todo.setStatus(TodoStatus.fromCode(todoDto.getStatus().getId()).getId());
        }
        todo.setTitle(todoDto.getTitle());
        Todo saved = todoRepo.save(todo);
        if (ObjectUtils.isEmpty(saved)) {
            return false;
        }
        return true;
    }

    @Override
    public TodoDto getTodoByID(Integer id) throws Exception {
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Invalid Todo ID"));
        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todoList = todoRepo.findAll();

        return todoList.stream().map(todo -> {
            TodoDto dto = new TodoDto();
            dto.setId(todo.getId());
            dto.setTitle(todo.getTitle());
            TodoStatus enumStatus = TodoStatus.fromCode(todo.getStatus());

            if (todo.getStatus() != null) {
                StatusDto statusDto = new StatusDto();
                statusDto.setId(todo.getStatus());
                statusDto.setName(enumStatus.getName());
                dto.setStatus(statusDto);
            }
            return dto;
        }).toList();
    }
}