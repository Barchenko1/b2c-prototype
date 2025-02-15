package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.dto.payload.CategoryDto;
import com.b2c.prototype.modal.dto.update.CategoryDtoUpdate;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.manager.AbstractTransitiveSelfEntityManager;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.tm.core.process.dao.transitive.ITransitiveSelfEntityDao;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CategoryManager extends AbstractTransitiveSelfEntityManager implements ICategoryManager {

    private final ICategoryDao categoryDao;

    public CategoryManager(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    protected ITransitiveSelfEntityDao getEntityDao() {
        return this.categoryDao;
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        Category newCategory = buildCategory(categoryDto);

        super.saveEntityTree(newCategory);
    }

    @Override
    public void updateCategory(CategoryDtoUpdate categoryDtoUpdate) {
        Category oldCategory = buildCategory(categoryDtoUpdate.getOldEntity());
        Category newCategory = buildCategory(categoryDtoUpdate.getNewEntity());

        CategoryDto oldCategoryDto = categoryDtoUpdate.getOldEntity();
        Parameter parameter =
                parameterFactory.createStringParameter("name", oldCategoryDto.getName());
        categoryDao.updateEntityTreeOldMain(newCategory, parameter);
    }

    @Override
    public void deleteCategory(CategoryDto categoryDto) {
        Category oldCategory = buildCategory(categoryDto);

        Parameter parameter =
                parameterFactory.createStringParameter("name", categoryDto.getName());
        categoryDao.deleteEntityTree(parameter);
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        Parameter parameter = new Parameter("name", categoryName);
        Optional<Category> optionalCategory =
                categoryDao.getOptionalTransitiveSelfEntity(parameter);

        if (optionalCategory.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalCategory.get();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getTransitiveSelfEntityList();
    }

    @Override
    public Map<TransitiveSelfEnum, List<Category>> getAllCategoriesTree() {
        return categoryDao.getTransitiveSelfEntitiesTree();
    }

    private Category buildCategory(CategoryDto categoryDto) {
        Category parentCategory = null;
        if (categoryDto.getParent() != null) {
            parentCategory = Category.builder()
                    .name(categoryDto.getParent().getName())
                    .parent(null)
                    .build();
        }

        Category category = Category.builder()
                .name(categoryDto.getName())
                .parent(parentCategory)
                .build();

        if (categoryDto.getChildNodeList() != null) {
            List<Category> childCategory = new ArrayList<>();
            for (CategoryDto childRequest : categoryDto.getChildNodeList()) {
                Category childCategoryEntity = Category.builder()
                        .name(childRequest.getName())
                        .parent(category)
                        .build();
                childCategory.add(childCategoryEntity);
            }
            category.setChildNodeList(childCategory);
        }

        return category;
    }

}
