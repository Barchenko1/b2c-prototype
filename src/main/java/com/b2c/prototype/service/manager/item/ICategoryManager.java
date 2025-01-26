package com.b2c.prototype.service.manager.item;

import com.b2c.prototype.modal.dto.payload.CategoryDto;
import com.b2c.prototype.modal.dto.update.CategoryDtoUpdate;
import com.b2c.prototype.modal.entity.item.Category;
import com.tm.core.util.TransitiveSelfEnum;

import java.util.List;
import java.util.Map;

public interface ICategoryManager {
    void createCategory(CategoryDto categoryDto);
    void updateCategory(CategoryDtoUpdate requestCategoryDto);
    void deleteCategory(CategoryDto categoryDto);

    Category getCategoryByCategoryName(String categoryName);
    List<Category> getAllCategories();
    Map<TransitiveSelfEnum, List<Category>> getAllCategoriesTree();
}
