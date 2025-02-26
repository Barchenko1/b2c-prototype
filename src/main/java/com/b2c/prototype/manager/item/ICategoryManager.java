package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.entity.item.Category;

import java.util.List;
import java.util.Map;

public interface ICategoryManager {
    void createCategory(CategoryDto categoryDto);
    void updateCategory(String parentCategoryValue, CategoryDto categoryDto);
    void deleteCategory(String categoryValue);

    Category getCategoryByCategoryName(String categoryValue);
    List<Category> getAllCategories();
    Map<String, List<Category>> getAllCategoriesTree();
}
