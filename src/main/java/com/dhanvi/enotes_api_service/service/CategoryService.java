package com.dhanvi.enotes_api_service.service;
import com.dhanvi.enotes_api_service.dto.CategoryDto;
import com.dhanvi.enotes_api_service.dto.CategoryResponseDto;
import com.dhanvi.enotes_api_service.model.Category;
import java.util.List;

public interface CategoryService {

    public boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();

    public List<CategoryResponseDto> getActiveCategory();

    CategoryDto getCategoryById(Integer id);

    Boolean deleteCategoryById(Integer id);
}
