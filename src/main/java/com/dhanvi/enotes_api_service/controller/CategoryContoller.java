package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryContoller {

    @Autowired
    CategoryService categoryService;

    @PostMapping("save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        Boolean saveCategory= categoryService.saveCategory(category);
        if(saveCategory){
            return new ResponseEntity<>("Saved successfully", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Could not save", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("get-categories")
    public ResponseEntity<?> getAllCategory(){
        List<Category> categories= categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categories)){
            return ResponseEntity.noContent().build();
        }
        else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }

    }
}
