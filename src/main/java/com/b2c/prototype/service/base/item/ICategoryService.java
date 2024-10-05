package com.b2c.prototype.service.base.item;

import com.b2c.prototype.modal.dto.request.RequestCategoryDto;
import com.b2c.prototype.modal.dto.update.RequestCategoryDtoUpdate;
import com.b2c.prototype.modal.entity.item.Category;
import com.tm.core.util.TransitiveSelfEnum;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    void createCategory(RequestCategoryDto requestCategoryDto);
    void updateCategory(RequestCategoryDtoUpdate requestCategoryDto);
    void deleteCategory(RequestCategoryDto requestCategoryDto);

    Category getCategoryByCategoryName(String categoryName);
    List<Category> getAllCategories();
    Map<TransitiveSelfEnum, List<Category>> getAllCategoriesTree();
}
