package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.dto.request.RequestCategoryDto;
import com.b2c.prototype.modal.dto.update.RequestCategoryDtoUpdate;
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
    public void createCategory(RequestCategoryDto requestCategoryDto) {
        Category newCategory = buildCategory(requestCategoryDto);

        super.saveEntityTree(newCategory);
        addNewEntityTreeToMap(entityCachedMap, newCategory);
    }

    @Override
    public void updateCategory(RequestCategoryDtoUpdate requestCategoryDtoUpdate) {
        Category oldCategory = buildCategory(requestCategoryDtoUpdate.getOldEntityDto());
        Category newCategory = buildCategory(requestCategoryDtoUpdate.getNewEntityDto());

        RequestCategoryDto oldRequestCategoryDto = requestCategoryDtoUpdate.getOldEntityDto();
        Parameter parameter =
                parameterFactory.createStringParameter("name", oldRequestCategoryDto.getName());
        categoryDao.updateEntityTreeOldMain(newCategory, parameter);
        updateEntityTreeOldMainMap(entityCachedMap, oldCategory, newCategory);
    }

    @Override
    public void deleteCategory(RequestCategoryDto requestCategoryDto) {
        Category oldCategory = buildCategory(requestCategoryDto);

        Parameter parameter =
                parameterFactory.createStringParameter("name", requestCategoryDto.getName());
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
