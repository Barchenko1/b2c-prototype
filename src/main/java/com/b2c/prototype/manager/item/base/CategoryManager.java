package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.util.CategoryUtil;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import lombok.extern.slf4j.Slf4j;

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
import static com.b2c.prototype.util.Constant.VALUE;

@Slf4j
public class CategoryManager implements ICategoryManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public CategoryManager(ICategoryDao categoryDao,
                           IQueryService queryService,
                           ITransformationFunctionService transformationFunctionService,
                           IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(categoryDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveCategoryList(List<CategoryDto> categoryDtoList) {
        createCategory(categoryDtoList);
    }

    @Override
    public void updateSingleCategory(CategoryDto categoryDto) {
        entityOperationManager.executeConsumer(session -> {
            Optional<Category> optionalCategory = queryService.getNamedQueryOptionalEntity(
                    session,
                    Category.class,
                    "Category.findByValue",
                    parameterFactory.createStringParameter(VALUE, categoryDto.getOldValue()));
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                List<String> values = getValues(category);
                List<CategoryDto> allNestedCategories = flattenCategory(categoryDto);
                List<String> duplicates = validateNoDuplicates(values, allNestedCategories);
                if (!duplicates.isEmpty()) {
                    throw new RuntimeException("Duplicate values found: " + duplicates);
                }
                category.setLabel(categoryDto.getLabel());
                category.setValue(categoryDto.getValue());
                session.merge(category);
            }
        });
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
        entityOperationManager.executeConsumer(session -> {
            Optional<Category> optionalCategory = queryService.getNamedQueryOptionalEntity(
                    session,
                    Category.class,
                    "Category.findByValue",
                    parameterFactory.createStringParameter(VALUE, categoryValue));
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                session.remove(category);
            }
        });
    }

    @Override
    public CategoryDto getCategoryByCategoryName(String categoryName) {
        AtomicReference<CategoryDto> categoryDto = new AtomicReference<>();
        entityOperationManager.executeConsumer(session -> {
            Optional<Category> optionalCategory = queryService.getNamedQueryOptionalEntity(
                    session,
                    Category.class,
                    "Category.findByValue",
                    parameterFactory.createStringParameter(VALUE, categoryName));
            categoryDto.set(optionalCategory
                    .map(CategoryUtil::toDto)
                    .orElseThrow(() -> new RuntimeException("Category not found")));
        });
        return categoryDto.get();
    }

    @Override
    public List<CategoryDto> getAllFirstLineCategories() {
        AtomicReference<List<CategoryDto>> categoryDtoList = new AtomicReference<>();
        entityOperationManager.executeConsumer(session -> {
            List<Category> categories = queryService.getNamedQueryEntityList(
                    session,
                    Category.class,
                    "Category.allParent");
            categoryDtoList.set(categories.stream()
                    .map(CategoryUtil::toDto)
                    .toList());
        });
        return categoryDtoList.get();
    }

    private Category updateEntity(CategoryDto categoryDto, Map<String, Category> existingCategoryMap) {
        if (categoryDto == null) {
            return null;
        }

        Category category = (categoryDto.getOldValue() != null)
                ? existingCategoryMap.get(categoryDto.getOldValue())
                : null;

        if (category == null) {
            category = new Category();
        }

        category.setLabel(categoryDto.getLabel());
        category.setValue(categoryDto.getValue());

        Map<String, Category> existingChildrenMap = category.getChildList().stream()
                .collect(Collectors.toMap(Category::getValue, child -> child));

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
        entityOperationManager.executeConsumer(session -> {
            List<Category> existingCategoryList = queryService.getNamedQueryEntityList(
                    session,
                    Category.class,
                    "Category.all");
            List<String> values = getAllValues(existingCategoryList);
            List<CategoryDto> allNestedCategories = flattenCategories(categoryDtoList);
            List<String> duplicates = validateNoDuplicates(values, allNestedCategories);
            if (!duplicates.isEmpty()) {
                throw new RuntimeException("Duplicate values found: " + duplicates);
            }
            Map<String, Category> existingCategoryMap = existingCategoryList.stream()
                    .collect(Collectors.toMap(Category::getValue, category -> category));

            categoryDtoList.forEach(categoryDto -> {
                Category updatedCategory = updateEntity(categoryDto, existingCategoryMap);
                session.merge(updatedCategory);
            });
        });
    }

}
