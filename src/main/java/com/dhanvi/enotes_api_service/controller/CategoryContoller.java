package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.CategoryResponseDto;
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
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory= categoryService.saveCategory(categoryDto);
        if(saveCategory){
            return new ResponseEntity<>("Saved successfully", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Could not save", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("get-categories")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> categories= categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categories)){
            return ResponseEntity.noContent().build();
        }
        else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }

    }

    @GetMapping("active-categories")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponseDto> categories= categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categories)){
            return ResponseEntity.noContent().build();
        }
        else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id){
        CategoryDto categoryDto= categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto)){
            return new ResponseEntity<>("Category not found with id"+id,HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        Boolean deleted= categoryService.deleteCategoryById(id);
        if(deleted){
            return new ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Category not deleted with id"+id,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
