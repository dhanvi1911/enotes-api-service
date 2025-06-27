package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.CategoryResponseDto;
import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.repository.CategoryRepo;
import com.dhanvi.enotes_api_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
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

        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
            category.setCreatedBy(1);
        }
        else {
            updateCategory(category);
        }

        Category saveCategory= categoryRepo.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        else
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> findByID=categoryRepo.findById(category.getId());

        if(findByID.isPresent()){
            Category category1= findByID.get();
            category.setIsDeleted(category1.getIsDeleted());
            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
            category.setCreatedBy(category1.getCreatedBy());
//            category.setUpdatedOn(category1.getUpdatedOn());
        }
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
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> findByID= categoryRepo.findByIdAndIsDeletedFalse(id);
        if(findByID.isPresent()){
            Category category = findByID.get();
            return mapper.map(category,CategoryDto.class);
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
