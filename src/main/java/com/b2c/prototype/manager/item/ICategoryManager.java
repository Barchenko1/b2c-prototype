package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;

import java.util.List;

public interface ICategoryManager {
    void saveCategory(CategoryDto categoryDtoList);
    void updateCategory(String regionCode, String categoryKey, CategoryDto categoryDto);
    void deleteCategory(String regionCode, String categoryKey);

    CategoryDto getCategory(String regionCode, String categoryKey);
    List<CategoryDto> getCategories(String regionCode);
}
