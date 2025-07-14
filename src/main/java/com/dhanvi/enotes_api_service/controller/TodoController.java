package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.TodoDto;
import com.dhanvi.enotes_api_service.model.Todo;
import com.dhanvi.enotes_api_service.service.TodoService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto){
        Boolean saved = todoService.saveTodo(todoDto);
        if(saved){
            return new ResponseEntity<>("Saved the Todo Successfully", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Todo could not be saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTodoByID(@PathVariable Integer id) throws Exception {
        TodoDto tododto = todoService.getTodoByID(id);
        if(ObjectUtils.isEmpty(tododto)){
            return CommonUtil.createErrorResponseMessage("Could not fetch the Todo", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(tododto,HttpStatus.FOUND);
    }

    @GetMapping("allTodos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDto> todoDtoList=todoService.getAllTodos();
        if (CollectionUtils.isEmpty(todoDtoList)){
            return CommonUtil.createErrorResponseMessage("Could note find any todos", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(todoDtoList,HttpStatus.FOUND);
    }

}
