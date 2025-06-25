package com.dhanvi.enotes_api_service.service;
import com.dhanvi.enotes_api_service.model.Category;
import java.util.List;

public interface CategoryService {

    public boolean saveCategory(Category category);
    public List<Category> getAllCategory();
}
