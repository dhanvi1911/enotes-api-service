package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.CategoryResponseDto;
import com.dhanvi.enotes_api_service.exception.ResourceNotFoundExceptionHandler;
import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.repository.CategoryRepo;
import com.dhanvi.enotes_api_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {

//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());

        Category category=mapper.map(categoryDto, Category.class);
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        Category saveCategory= categoryRepo.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        else
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories= categoryRepo.findByIsDeletedFalse();

        List<CategoryDto> categoryDtos=categories.stream().map(cat->mapper.map(cat,CategoryDto.class)).toList();
        return categoryDtos;
    }

    @Override
    public List<CategoryResponseDto> getActiveCategory() {
        List<Category> categories= categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponseDto> categoryResponseDtos= categories.stream().map(category -> mapper.map(category, CategoryResponseDto.class)).toList();
        return categoryResponseDtos;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception{
        Category findByID= categoryRepo.findByIdAndIsDeletedFalse(id).
                orElseThrow(()-> new ResourceNotFoundExceptionHandler("Category not found with id" + id));
        if(!ObjectUtils.isEmpty(findByID)){
            return mapper.map(findByID,CategoryDto.class);
        }
        else
        return null;

    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
       Optional<Category> findByID=categoryRepo.findById(id);
        if(findByID.isPresent()){
            Category category=findByID.get();
            category.setIsDeleted(true);
            categoryRepo.save(category);
            return true;
        }
        else
            return false;
    }
}
