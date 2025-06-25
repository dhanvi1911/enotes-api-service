package com.dhanvi.enotes_api_service.service.impl;

import com.dhanvi.enotes_api_service.model.Category;
import com.dhanvi.enotes_api_service.repository.CategoryRepo;
import com.dhanvi.enotes_api_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        Category saveCategory= categoryRepo.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        else
        return true;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories= categoryRepo.findAll();
        return categories;
    }
}
