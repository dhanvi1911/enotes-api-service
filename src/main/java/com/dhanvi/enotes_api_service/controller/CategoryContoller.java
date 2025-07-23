package com.dhanvi.enotes_api_service.controller;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.CategoryResponseDto;
import com.dhanvi.enotes_api_service.handler.GenericResponse;
import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.service.CategoryService;
import com.dhanvi.enotes_api_service.util.CommonUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto){
        Boolean saveCategory= categoryService.saveCategory(categoryDto);
        if(saveCategory){
            return CommonUtil.createBuildResponseMessage("Saved successfully",HttpStatus.CREATED);
//            return new ResponseEntity<>("Saved successfully", HttpStatus.CREATED);
        }
        else {
            return CommonUtil.createErrorResponseMessage("Could not save", HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>("Could not save", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("get-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> categories= categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categories)){
            return ResponseEntity.noContent().build();
        }
        else {
            return CommonUtil.createBuildResponse(categories, HttpStatus.OK);
//            return new ResponseEntity<>(categories, HttpStatus.OK);
        }

    }

    @GetMapping("active-categories")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponseDto> categories= categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categories)){
            return ResponseEntity.noContent().build();
        }
        else {
            return CommonUtil.createBuildResponse(categories, HttpStatus.OK);
//            return new ResponseEntity<>(categories, HttpStatus.OK);
        }

    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception {
        CategoryDto categoryDto= categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto)){
            return CommonUtil.createErrorResponseMessage("Category not found with id"+id,HttpStatus.NOT_FOUND);
//            return new ResponseEntity<>("Category not found with id"+id,HttpStatus.NOT_FOUND);
        }
        else{
            return CommonUtil.createBuildResponse(categoryDto,HttpStatus.OK);
//            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        Boolean deleted= categoryService.deleteCategoryById(id);
        if(deleted){
            return CommonUtil.createBuildResponseMessage("Category deleted successfully",HttpStatus.OK);
//            return new ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
        }
        else{
            return CommonUtil.createErrorResponseMessage("Category not deleted with id"+id,HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>("Category not deleted with id"+id,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
