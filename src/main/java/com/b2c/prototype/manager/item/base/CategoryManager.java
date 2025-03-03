package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CategoryManager implements ICategoryManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public CategoryManager(ICategoryDao categoryDao,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService,
                           IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(categoryDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        Category newCategory = buildCategory(categoryDto);
        entityOperationManager.saveEntity(newCategory);
    }

    @Override
    public void updateCategory(String parentCategoryValue, CategoryDto categoryDto) {
//        Category oldCategory = buildCategory(categoryDto.getOldEntity());
//        Category newCategory = buildCategory(categoryDto.getNewEntity());

//        CategoryDto oldCategoryDto = categoryDtoUpdate.getOldEntity();
//        Parameter parameter =
//                parameterFactory.createStringParameter("name", oldCategoryDto.getRoot().getValue());
        Category category = buildCategory(categoryDto);
        entityOperationManager.updateEntity(category);
    }

    @Override
    public void deleteCategory(String categoryValue) {
        Parameter parameter =
                parameterFactory.createStringParameter("value", categoryValue);
        entityOperationManager.deleteEntity(parameter);
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        Parameter parameter = new Parameter("name", categoryName);
        Optional<Category> optionalCategory = entityOperationManager.getNamedQueryEntity("", parameter);

        if (optionalCategory.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalCategory.get();
    }

    @Override
    public List<Category> getAllCategories() {
        return entityOperationManager.getNamedQueryEntityList("");
    }

    @Override
    public Map<String, List<Category>> getAllCategoriesTree() {
        return null;
    }

    private Category buildCategory(CategoryDto categoryDto) {
        Category parentCategory = null;
        if (categoryDto.getParent() != null) {
            parentCategory = Category.builder()
                    .value(categoryDto.getParent().getRoot().getValue())
                    .parent(null)
                    .build();
        }

        Category category = Category.builder()
                .value(categoryDto.getRoot().getValue())
                .parent(parentCategory)
                .build();

        if (categoryDto.getChildNodeList() != null) {
            List<Category> childCategory = new ArrayList<>();
            for (CategoryDto childRequest : categoryDto.getChildNodeList()) {
                Category childCategoryEntity = Category.builder()
                        .value(childRequest.getRoot().getValue())
                        .parent(category)
                        .build();
                childCategory.add(childCategoryEntity);
            }
            category.setChildNodeList(childCategory);
        }

        return category;
    }

}
