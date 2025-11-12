package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;

import java.util.List;
import java.util.Map;

public interface ICategoryProcess {
    void createCategory(Map<String, String> requestParams, CategoryDto categoryDto);
    void updateCategory(Map<String, String> requestParams, CategoryDto categoryDto);
    void deleteCategory(Map<String, String> requestParams);

    CategoryDto getCategory(Map<String, String> requestParams);
    List<CategoryDto> getCategories(Map<String, String> requestParams);
}
