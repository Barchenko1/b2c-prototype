package com.b2c.prototype.processor.item;

import com.b2c.prototype.manager.item.IBrandManager;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemProcess implements IItemProcess {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IBrandManager brandManager;

    public ItemProcess(IBrandManager brandManager) {
        this.brandManager = brandManager;
    }

    @Override
    public void saveBrand(Map<String, Object> payload) {
        ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
        brandManager.saveEntity();
    }

    @Override
    public void putBrand(Map<String, Object> payload, String value) {

    }

    @Override
    public void patchBrand(Map<String, Object> payload, String value) {

    }

    @Override
    public void deleteBrand(String value) {

    }

    @Override
    public List<BrandDto> getBrandDtoList(String location, String serviceId) {
        return List.of();
    }

    @Override
    public BrandDto getBrand(String location, String serviceId, String value) {
        return null;
    }

    @Override
    public void saveCategoryList(List<CategoryDto> categoryDtoList) {

    }

    @Override
    public void updateSingleCategory(CategoryDto categoryDto) {

    }

    @Override
    public void updateCategory(List<CategoryDto> categoryDtoList) {

    }

    @Override
    public void deleteCategory(String categoryValue) {

    }

    @Override
    public CategoryDto getCategoryByCategoryName(String categoryName) {
        return null;
    }

    @Override
    public List<CategoryDto> getAllFirstLineCategories() {
        return List.of();
    }
}
