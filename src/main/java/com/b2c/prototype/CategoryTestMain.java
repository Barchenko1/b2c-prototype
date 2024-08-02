package com.b2c.prototype;

import com.b2c.prototype.dao.basic.BasicCategoryDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.client.dto.request.RequestCategoryDto;
import com.b2c.prototype.modal.client.dto.update.RequestCategoryDtoUpdate;
import com.b2c.prototype.modal.client.entity.item.Category;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.processor.base.CategoryEntityStringMapWrapper;
import com.b2c.prototype.service.client.item.base.CategoryService;
import com.b2c.prototype.service.client.item.ICategoryService;
import com.tm.core.configuration.ConfigDbType;
import com.tm.core.configuration.factory.ConfigurationSessionFactory;
import com.tm.core.configuration.factory.IConfigurationSessionFactory;
import com.tm.core.util.TransitiveSelfEnum;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.Query.SELECT_CATEGORY_BY_NAME;

public class CategoryTestMain {
    public static void main(String[] args) {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConfigDbType.XML
        );
        SessionFactory sessionFactory = configurationSessionFactory.getSessionFactory();
        ICategoryDao categoryDao = new BasicCategoryDao(sessionFactory);

        Map<String, Category> categoryMap = new HashMap<>();
        IEntityStringMapWrapper<Category> categoryEntityMapWrapper =
                new CategoryEntityStringMapWrapper(categoryDao, categoryMap, SELECT_CATEGORY_BY_NAME);
        ICategoryService categoryService = new CategoryService(categoryDao, categoryEntityMapWrapper);

        RequestCategoryDto requestCategoryDto1 = getCategory1();
        RequestCategoryDto requestCategoryDto2 = getCategory2();
        RequestCategoryDtoUpdate requestCategoryDtoUpdate = new RequestCategoryDtoUpdate();
        requestCategoryDtoUpdate.setOldEntityDto(requestCategoryDto1);
        requestCategoryDtoUpdate.setNewEntityDto(requestCategoryDto2);

        categoryService.createCategory(requestCategoryDto1);
        Category category = categoryService.getCategoryByCategoryName("BMW");
        categoryService.updateCategory(requestCategoryDtoUpdate);
        Map<TransitiveSelfEnum, List<Category>> categoriesMap = categoryService.getAllCategoriesTree();
        categoryService.deleteCategory(requestCategoryDto2);
    }

    private static RequestCategoryDto getCategory1() {
        RequestCategoryDto parentCategoryDto = new RequestCategoryDto();
        parentCategoryDto.setName("car");

        RequestCategoryDto requestCategoryDto = new RequestCategoryDto();
        requestCategoryDto.setParent(parentCategoryDto);
        requestCategoryDto.setName("BMW");

        RequestCategoryDto childCategoryDto = new RequestCategoryDto();
        childCategoryDto.setParent(requestCategoryDto);
        childCategoryDto.setName("truck");

        requestCategoryDto.setChildNodeList(Collections.singletonList(childCategoryDto));

        return requestCategoryDto;
    }

    private static RequestCategoryDto getCategory2() {
        RequestCategoryDto parentCategoryDto = new RequestCategoryDto();
        parentCategoryDto.setName("car1");

        RequestCategoryDto requestCategoryDto = new RequestCategoryDto();
        requestCategoryDto.setParent(parentCategoryDto);
        requestCategoryDto.setName("BMW1");

        RequestCategoryDto childCategoryDto = new RequestCategoryDto();
        childCategoryDto.setParent(requestCategoryDto);
        childCategoryDto.setName("truck1");

        requestCategoryDto.setChildNodeList(Collections.singletonList(childCategoryDto));

        return requestCategoryDto;
    }
}
