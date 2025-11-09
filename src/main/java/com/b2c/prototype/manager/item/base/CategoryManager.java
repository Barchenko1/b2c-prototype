package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.util.CategoryUtil;
import com.nimbusds.jose.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.CategoryUtil.flattenCategories;
import static com.b2c.prototype.util.CategoryUtil.flattenCategory;
import static com.b2c.prototype.util.CategoryUtil.getAllValues;
import static com.b2c.prototype.util.CategoryUtil.getValues;
import static com.b2c.prototype.util.CategoryUtil.validateNoDuplicates;
import static com.b2c.prototype.util.Constant.KEY;

@Slf4j
@Service
public class CategoryManager implements ICategoryManager {

    private final IGeneralEntityDao generalEntityDao;

    public CategoryManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    @Override
    public void saveCategoryList(List<CategoryDto> categoryDtoList) {
        createCategory(categoryDtoList);
    }

    @Override
    public void updateSingleCategory(CategoryDto categoryDto) {
        Optional<Category> optionalCategory = generalEntityDao.findOptionEntity(
                "Category.findByKey",
                Pair.of(KEY, categoryDto.getOldKey()));
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            List<String> values = getValues(category);
            List<CategoryDto> allNestedCategories = flattenCategory(categoryDto);
            List<String> duplicates = validateNoDuplicates(values, allNestedCategories);
            if (!duplicates.isEmpty()) {
                throw new RuntimeException("Duplicate values found: " + duplicates);
            }
            category.setValue(categoryDto.getValue());
            category.setKey(categoryDto.getKey());
            generalEntityDao.mergeEntity(category);
        }
    }

    @Override
    public void updateCategory(List<CategoryDto> categoryDtoList) {
        try {
            createCategory(categoryDtoList);
        } catch (Exception e) {
            throw new RuntimeException("Duplicate key value", e);
        }
    }

    @Override
    public void deleteCategory(String categoryValue) {
        Optional<Category> optionalCategory = generalEntityDao.findOptionEntity(
                "Category.findByKey",
                Pair.of(KEY, categoryValue));
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            generalEntityDao.removeEntity(category);
        }
    }

    @Override
    public CategoryDto getCategoryByCategoryName(String categoryName) {
        AtomicReference<CategoryDto> categoryDto = new AtomicReference<>();
        Optional<Category> optionalCategory = generalEntityDao.findOptionEntity(
                "Category.findByKey",
                Pair.of(KEY, categoryName));
        categoryDto.set(optionalCategory
                .map(CategoryUtil::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found")));
        return categoryDto.get();
    }

    @Override
    public List<CategoryDto> getAllFirstLineCategories() {
        AtomicReference<List<CategoryDto>> categoryDtoList = new AtomicReference<>();
        List<Category> categories = generalEntityDao.findEntityList(
                "Category.allParent", (Pair<String, ?>) null);
        categoryDtoList.set(categories.stream()
                .map(CategoryUtil::toDto)
                .toList());
        return categoryDtoList.get();
    }

    private Category updateEntity(CategoryDto categoryDto, Map<String, Category> existingCategoryMap) {
        if (categoryDto == null) {
            return null;
        }

        Category category = (categoryDto.getOldKey() != null)
                ? existingCategoryMap.get(categoryDto.getOldKey())
                : null;

        if (category == null) {
            category = new Category();
        }

        category.setValue(categoryDto.getValue());
        category.setKey(categoryDto.getKey());

        Map<String, Category> existingChildrenMap = category.getChildList().stream()
                .collect(Collectors.toMap(Category::getKey, child -> child));

        List<Category> updatedChildren = new ArrayList<>();

        if (categoryDto.getChildList() != null) {
            for (CategoryDto childDto : categoryDto.getChildList()) {
                Category updatedChild = updateEntity(childDto, existingChildrenMap);
                updatedChild.setParent(category);
                updatedChildren.add(updatedChild);
            }
        }

        category.getChildList().clear();
        category.getChildList().addAll(updatedChildren);

        return category;
    }

    private void createCategory(List<CategoryDto> categoryDtoList) {
        List<Category> existingCategoryList = generalEntityDao.findEntityList(
                "Category.all", (Pair<String, ?>) null);
        List<String> values = getAllValues(existingCategoryList);
        List<CategoryDto> allNestedCategories = flattenCategories(categoryDtoList);
        List<String> duplicates = validateNoDuplicates(values, allNestedCategories);
        if (!duplicates.isEmpty()) {
            throw new RuntimeException("Duplicate values found: " + duplicates);
        }
        Map<String, Category> existingCategoryMap = existingCategoryList.stream()
                .collect(Collectors.toMap(Category::getKey, category -> category));

        categoryDtoList.forEach(categoryDto -> {
            Category updatedCategory = updateEntity(categoryDto, existingCategoryMap);
            generalEntityDao.mergeEntity(updatedCategory);
        });
    }

}
