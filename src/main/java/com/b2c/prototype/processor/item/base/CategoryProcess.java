package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.processor.item.ICategoryProcess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryProcess implements ICategoryProcess {

    private final ICategoryManager categoryManager;

    public CategoryProcess(ICategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Override
    public void createCategory(Map<String, String> requestParams, CategoryDto categoryDto) {
        categoryManager.saveCategory(categoryDto);
    }

    @Override
    public void updateCategory(Map<String, String> requestParams, CategoryDto categoryDto) {
        String regionCode = requestParams.get("tenant");
        String categoryKey = requestParams.get("category");
        categoryManager.updateCategory(regionCode, categoryKey, categoryDto);
    }

    @Override
    public void deleteCategory(Map<String, String> requestParams) {
        String regionCode = requestParams.get("tenant");
        String categoryKey = requestParams.get("category");
        categoryManager.deleteCategory(regionCode, categoryKey);
    }

    @Override
    public CategoryDto getCategory(Map<String, String> requestParams) {
        String regionCode = requestParams.get("tenant");
        String categoryName = requestParams.get("category");
        return categoryManager.getCategory(regionCode, categoryName);
    }

    @Override
    public List<CategoryDto> getCategories(Map<String, String> requestParams) {
        String regionCode = requestParams.get("tenant");
        return categoryManager.getCategories(regionCode);
    }

}
