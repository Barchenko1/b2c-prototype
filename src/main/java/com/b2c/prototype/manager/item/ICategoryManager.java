package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;

import java.util.List;

public interface ICategoryManager {
    void saveCategoryList(List<CategoryDto> categoryDtoList);
    void updateSingleCategory(CategoryDto categoryDto);
    void updateCategory(List<CategoryDto> categoryDtoList);
    void deleteCategory(String categoryValue);

    CategoryDto getCategoryByCategoryName(String categoryName);
    List<CategoryDto> getAllFirstLineCategories();
}
