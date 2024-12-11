package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.dto.request.CategoryDto;
import com.b2c.prototype.modal.dto.update.CategoryDtoUpdate;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.service.transitive.AbstractTransitiveSelfEntityService;
import com.b2c.prototype.service.processor.item.ICategoryService;
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
public class CategoryService extends AbstractTransitiveSelfEntityService implements ICategoryService {

    private final ICategoryDao categoryDao;
    private final IEntityCachedMap entityCachedMap;

    public CategoryService(ICategoryDao categoryDao,
                           IEntityCachedMap entityCachedMap) {
        this.categoryDao = categoryDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    protected ITransitiveSelfEntityDao getEntityDao() {
        return this.categoryDao;
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        Category newCategory = buildCategory(categoryDto);

        super.saveEntityTree(newCategory);
        addNewEntityTreeToMap(entityCachedMap, newCategory);
    }

    @Override
    public void updateCategory(CategoryDtoUpdate categoryDtoUpdate) {
        Category oldCategory = buildCategory(categoryDtoUpdate.getOldEntityDto());
        Category newCategory = buildCategory(categoryDtoUpdate.getNewEntityDto());

        CategoryDto oldCategoryDto = categoryDtoUpdate.getOldEntityDto();
        Parameter parameter =
                parameterFactory.createStringParameter("name", oldCategoryDto.getName());
        categoryDao.updateEntityTreeOldMain(newCategory, parameter);
        updateEntityTreeOldMainMap(entityCachedMap, oldCategory, newCategory);
    }

    @Override
    public void deleteCategory(CategoryDto categoryDto) {
        Category oldCategory = buildCategory(categoryDto);

        Parameter parameter =
                parameterFactory.createStringParameter("name", categoryDto.getName());
        categoryDao.deleteEntityTree(parameter);
        deleteEntityFromMap(entityCachedMap, oldCategory);
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

    private <E extends TransitiveSelfEntity> void deleteEntityFromMap(IEntityCachedMap entityCachedMap, E entity) {
        entityCachedMap.removeEntity(Category.class, entity.getRootField());
        if (entity.getChildNodeList() != null) {
            entity.getChildNodeList().forEach(childNode ->
                    entityCachedMap.removeEntity(Category.class, childNode.getRootField()));
        }
    }

    private <E extends TransitiveSelfEntity> void addNewEntityTreeToMap(IEntityCachedMap entityMapWrapper, E entity) {
        if (entity.getParent() != null) {
            entityMapWrapper.putEntity(Category.class, entity.getParent().getRootField(), entity.getParent());
        }
        entityMapWrapper.putEntity(Category.class, entity.getRootField(), entity);
        if (entity.getChildNodeList() != null) {
            entity.getChildNodeList().forEach(childNode ->
                    entityMapWrapper.putEntity(Category.class, childNode.getRootField(), (E) childNode));
        }
    }

    private <E extends TransitiveSelfEntity> void updateEntityTreeOldMainMap(IEntityCachedMap entityMapWrapper, E oldEntity, E newEntity) {
        if (oldEntity.getParent() != null) {
            entityMapWrapper.updateEntity(
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
