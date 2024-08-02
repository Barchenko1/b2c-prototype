package com.b2c.prototype.test.service;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.client.dto.request.RequestCategoryDto;
import com.b2c.prototype.modal.client.dto.update.RequestCategoryDtoUpdate;
import com.b2c.prototype.modal.client.entity.item.Category;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.item.ICategoryService;
import com.b2c.prototype.service.client.item.base.CategoryService;
import com.tm.core.modal.EntityFinder;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.util.TransitiveSelfEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.util.Query.SELECT_ALL_CATEGORIES;
import static com.b2c.prototype.util.Query.SELECT_CATEGORY_BY_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    private ICategoryDao categoryDao;

    @Mock
    private IEntityStringMapWrapper<Category> categoryEntityMapWrapper;

    private ICategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryDao, categoryEntityMapWrapper);
    }

    @Test
    public void testCreateCategory() {
        // Prepare data
        RequestCategoryDto requestCategoryDto = new RequestCategoryDto();
        requestCategoryDto.setName("New Category");

        Category newCategory = new Category();
        newCategory.setName("New Category");

        // Mock behavior
        doNothing().when(categoryDao).saveEntityTree(newCategory);

        // Call method
        categoryService.createCategory(requestCategoryDto);

        // Capture and verify
        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryDao).saveEntityTree(categoryCaptor.capture());
    }

    @Test
    public void testUpdateCategory() {
        // Arrange
        RequestCategoryDto oldEntityDto = new RequestCategoryDto();
        oldEntityDto.setName("Old Category");
        RequestCategoryDto newEntityDto = new RequestCategoryDto();
        newEntityDto.setName("New Category");

        RequestCategoryDtoUpdate requestCategoryDtoUpdate = new RequestCategoryDtoUpdate();
        requestCategoryDtoUpdate.setOldEntityDto(oldEntityDto);
        requestCategoryDtoUpdate.setNewEntityDto(newEntityDto);

        Category oldCategory = new Category();
        oldCategory.setName("Old Category");

        Category newCategory = new Category();
        newCategory.setName("New Category");

        when(categoryDao.getOptionalEntityBySQLQueryWithParams(eq(SELECT_CATEGORY_BY_NAME), anyString()))
                .thenReturn(Optional.of(oldCategory));

        doNothing().when(categoryDao).updateEntityTree(any(EntityFinder.class), eq(newCategory));

        // Act
        categoryService.updateCategory(requestCategoryDtoUpdate);

        // Assert
        verify(categoryDao, times(1)).updateEntityTree(any(EntityFinder.class), eq(newCategory));
    }

    @Test
    public void testDeleteCategory() {
        // Prepare data
        RequestCategoryDto requestCategoryDto = new RequestCategoryDto();
        requestCategoryDto.setName("Category to Delete");

        Category categoryToDelete = new Category();
        categoryToDelete.setName("Category to Delete");

        EntityFinder entityFinder = new EntityFinder(SELECT_CATEGORY_BY_NAME, requestCategoryDto.getName());

        // Mock behavior
        doNothing().when(categoryDao).deleteEntityTree(any(EntityFinder.class));

        // Call method
        categoryService.deleteCategory(requestCategoryDto);

        // Capture and verify
        verify(categoryDao, times(1)).deleteEntityTree(any());
    }

    @Test
    public void testGetCategoryByCategoryName() {
        // Prepare data
        String categoryName = "Existing Category";
        Category expectedCategory = new Category();
        expectedCategory.setName(categoryName);

        // Mock behavior
        when(categoryDao.getOptionalEntityBySQLQueryWithParams(SELECT_CATEGORY_BY_NAME, categoryName))
                .thenReturn(Optional.of(expectedCategory));

        // Call method
        Category result = categoryService.getCategoryByCategoryName(categoryName);

        // Verify
        assertEquals(expectedCategory, result);
    }

    @Test
    public void testGetAllCategories() {
        // Prepare data
        List<TransitiveSelfEntity> expectedCategories = Arrays.asList(new Category(), new Category());

        // Mock behavior
        when(categoryDao.getEntityListBySQLQuery(SELECT_ALL_CATEGORIES)).thenReturn(expectedCategories);

        // Call method
        List<Category> result = categoryService.getAllCategories();

        // Verify
        assertEquals(expectedCategories, result);
    }

    @Test
    public void testGetAllCategoriesTree() {
        // Prepare data
        Map<TransitiveSelfEnum, List<TransitiveSelfEntity>> expectedTree = new HashMap<>();

        // Mock behavior
        when(categoryDao.getEntitiesTreeBySQLQuery(anyString())).thenReturn(expectedTree);

        // Call method
        Map<TransitiveSelfEnum, List<Category>> result = categoryService.getAllCategoriesTree();

        // Verify
        assertEquals(expectedTree, result);
        verify(categoryDao, times(1)).getEntitiesTreeBySQLQuery(anyString());

    }
}