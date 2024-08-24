package com.b2c.prototype.service.item.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.dto.request.RequestCategoryDto;
import com.b2c.prototype.modal.dto.update.RequestCategoryDtoUpdate;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.b2c.prototype.service.item.ICategoryService;
import com.tm.core.processor.EntityFinder;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.util.Query.SELECT_CATEGORY_BY_NAME;
import static com.b2c.prototype.util.Query.SELECT_ALL_CATEGORIES;

@Slf4j
public class CategoryService implements ICategoryService {

    private final ICategoryDao categoryDao;
    private final IEntityStringMapWrapper<Category> categoryEntityMapWrapper;

    public CategoryService(ICategoryDao categoryDao,
                           IEntityStringMapWrapper<Category> categoryEntityMapWrapper) {
        this.categoryDao = categoryDao;
        this.categoryEntityMapWrapper = categoryEntityMapWrapper;
    }

    @Override
    public void createCategory(RequestCategoryDto requestCategoryDto) {
        Category newCategory = buildCategory(requestCategoryDto);
        categoryDao.saveEntityTree(newCategory);
        addNewEntityTreeToMap(categoryEntityMapWrapper, newCategory);
    }

    @Override
    public void updateCategory(RequestCategoryDtoUpdate requestCategoryDtoUpdate) {
        Category oldCategory = buildCategory(requestCategoryDtoUpdate.getOldEntityDto());
        Category newCategory = buildCategory(requestCategoryDtoUpdate.getNewEntityDto());
        EntityFinder entityFinder =
                new EntityFinder(SELECT_CATEGORY_BY_NAME, requestCategoryDtoUpdate.getOldEntityDto().getName());
        categoryDao.updateEntityTree(entityFinder, newCategory);
        updateEntityTreeMap(categoryEntityMapWrapper, oldCategory, newCategory);
    }

    @Override
    public void deleteCategory(RequestCategoryDto requestCategoryDto) {
        Category oldCategory = buildCategory(requestCategoryDto);
        EntityFinder entityFinder =
                new EntityFinder(SELECT_CATEGORY_BY_NAME, Category.class, requestCategoryDto.getName());
        categoryDao.deleteEntityTree(entityFinder);
        deleteEntityFromMap(categoryEntityMapWrapper, oldCategory);
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        Optional<Category> optionalCategory =
                categoryDao.getOptionalEntityBySQLQueryWithParams(SELECT_CATEGORY_BY_NAME, categoryName);

        if (optionalCategory.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalCategory.get();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getEntityListBySQLQuery(SELECT_ALL_CATEGORIES);
    }

    @Override
    public Map<TransitiveSelfEnum, List<Category>> getAllCategoriesTree() {
        return categoryDao.getEntitiesTreeBySQLQuery(SELECT_ALL_CATEGORIES);
    }

    private Category buildCategory(RequestCategoryDto requestCategoryDto) {
        Category parentCategory = null;
        if (requestCategoryDto.getParent() != null) {
            parentCategory = Category.builder()
                    .name(requestCategoryDto.getParent().getName())
                    .parent(null)
                    .build();
        }

        Category category = Category.builder()
                .name(requestCategoryDto.getName())
                .parent(parentCategory)
                .build();

        if (requestCategoryDto.getChildNodeList() != null) {
            List<Category> childCategory = new ArrayList<>();
            for (RequestCategoryDto childRequest : requestCategoryDto.getChildNodeList()) {
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

    private <E extends TransitiveSelfEntity> void deleteEntityFromMap(IEntityStringMapWrapper<E> entityMapWrapper, E entity) {
        entityMapWrapper.removeEntity(entity.getRootField());
        if (entity.getChildNodeList() != null) {
            entity.getChildNodeList().forEach(childNode ->
                    entityMapWrapper.removeEntity(childNode.getRootField()));
        }
    }

    private <E extends TransitiveSelfEntity> void addNewEntityTreeToMap(IEntityStringMapWrapper<E> entityMapWrapper, E entity) {
        if (entity.getParent() != null) {
            entityMapWrapper.putEntity(entity.getParent().getRootField(), entity.getParent());
        }
        entityMapWrapper.putEntity(entity.getRootField(), entity);
        if (entity.getChildNodeList() != null) {
            entity.getChildNodeList().forEach(childNode ->
                    entityMapWrapper.putEntity(childNode.getRootField(), (E) childNode));
        }
    }

    private <E extends TransitiveSelfEntity> void updateEntityTreeMap(IEntityStringMapWrapper<E> entityMapWrapper, E oldEntity, E newEntity) {
        if (oldEntity.getParent() != null) {
            entityMapWrapper.updateEntity(
                    oldEntity.getParent().getRootField(),
                    newEntity.getParent().getRootField(),
                    newEntity.getParent());
        }

        entityMapWrapper.removeEntity(oldEntity.getRootField());
        entityMapWrapper.putEntity(newEntity.getRootField(), newEntity);

        List<E> oldChildList = oldEntity.getChildNodeList();
        List<E> newChildList = newEntity.getChildNodeList();
        if (oldEntity.getChildNodeList() != null) {
            for (int i = 0; i < oldChildList.size(); i++) {
                entityMapWrapper.removeEntity(oldChildList.get(i).getRootField());
                entityMapWrapper.putEntity(newChildList.get(i).getRootField(), (E) newEntity.getChildNodeList().get(i));
            }
        }
    }

}
