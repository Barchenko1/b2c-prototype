package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;

import java.util.List;
import java.util.Map;

public interface IItemProcess {
    void saveBrand(final Map<String, Object> payload);
    void putBrand(final Map<String, Object> payload, final String value);
    void patchBrand(final Map<String, Object> payload, final String value);
    void deleteBrand(final String value);

    List<BrandDto> getBrandDtoList(final String location, final String serviceId);
    BrandDto getBrand(final String location, final String serviceId, final String value);

    void saveCategoryList(List<CategoryDto> categoryDtoList);
    void updateSingleCategory(CategoryDto categoryDto);
    void updateCategory(List<CategoryDto> categoryDtoList);
    void deleteCategory(String categoryValue);

    CategoryDto getCategoryByCategoryName(String categoryName);
    List<CategoryDto> getAllFirstLineCategories();
}
