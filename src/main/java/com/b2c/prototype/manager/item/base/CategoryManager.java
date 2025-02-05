package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.dto.payload.CategoryDto;
import com.b2c.prototype.modal.dto.update.CategoryDtoUpdate;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.manager.AbstractTransitiveSelfEntityManager;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.tm.core.dao.transitive.ITransitiveSelfEntityDao;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CategoryManager extends AbstractTransitiveSelfEntityManager implements ICategoryManager {

    private final ICategoryDao categoryDao;
    private final IConstantsScope singleValueMap;

    public CategoryManager(ICategoryDao categoryDao,
                           IConstantsScope singleValueMap) {
        this.categoryDao = categoryDao;
        this.singleValueMap = singleValueMap;
    }

    @Override
    protected ITransitiveSelfEntityDao getEntityDao() {
        return this.categoryDao;
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        Category newCategory = buildCategory(categoryDto);

        super.saveEntityTree(newCategory);
        addNewEntityTreeToMap(singleValueMap, newCategory);
    }

    @Override
    public void updateCategory(CategoryDtoUpdate categoryDtoUpdate) {
        Category oldCategory = buildCategory(categoryDtoUpdate.getOldEntity());
        Category newCategory = buildCategory(categoryDtoUpdate.getNewEntity());

        CategoryDto oldCategoryDto = categoryDtoUpdate.getOldEntity();
        Parameter parameter =
                parameterFactory.createStringParameter("name", oldCategoryDto.getName());
        categoryDao.updateEntityTreeOldMain(newCategory, parameter);
        updateEntityTreeOldMainMap(singleValueMap, oldCategory, newCategory);
    }

    @Override
    public void deleteCategory(CategoryDto categoryDto) {
        Category oldCategory = buildCategory(categoryDto);

        Parameter parameter =
                parameterFactory.createStringParameter("name", categoryDto.getName());
        categoryDao.deleteEntityTree(parameter);
        deleteEntityFromMap(singleValueMap, oldCategory);
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

    private <E extends TransitiveSelfEntity> void deleteEntityFromMap(IConstantsScope singleValueMap, E entity) {
        singleValueMap.removeEntity(Category.class, entity.getRootField());
        if (entity.getChildNodeList() != null) {
            entity.getChildNodeList().forEach(childNode ->
                    singleValueMap.removeEntity(Category.class, childNode.getRootField()));
        }
    }

    private <E extends TransitiveSelfEntity> void addNewEntityTreeToMap(IConstantsScope entityMapWrapper, E entity) {
        if (entity.getParent() != null) {
            entityMapWrapper.putEntity(Category.class, entity.getParent().getRootField(), entity.getParent());
        }
        entityMapWrapper.putEntity(Category.class, entity.getRootField(), entity);
        if (entity.getChildNodeList() != null) {
            entity.getChildNodeList().forEach(childNode ->
                    entityMapWrapper.putEntity(Category.class, childNode.getRootField(), (E) childNode));
        }
    }

    private <E extends TransitiveSelfEntity> void updateEntityTreeOldMainMap(IConstantsScope entityMapWrapper, E oldEntity, E newEntity) {
        if (oldEntity.getParent() != null) {
            entityMapWrapper.putRemoveEntity(
                    Category.class,
                    oldEntity.getParent().getRootField(),
                    newEntity.getParent().getRootField(),
                    newEntity.getParent());
        }

        entityMapWrapper.removeEntity(Category.class, oldEntity.getRootField());
        entityMapWrapper.putEntity(Category.class, newEntity.getRootField(), newEntity);

        List<E> oldChildList = oldEntity.getChildNodeList();
        List<E> newChildList = newEntity.getChildNodeList();
        if (oldEntity.getChildNodeList() != null) {
            for (int i = 0; i < oldChildList.size(); i++) {
                entityMapWrapper.removeEntity(Category.class, oldChildList.get(i).getRootField());
                entityMapWrapper.putEntity(Category.class, newChildList.get(i).getRootField(), (E) newEntity.getChildNodeList().get(i));
            }
        }
    }

}
