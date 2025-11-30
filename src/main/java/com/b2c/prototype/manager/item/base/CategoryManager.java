package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CategoryCascade;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;
import static com.b2c.prototype.util.Util.getUUID;

@Slf4j
@Service
public class CategoryManager implements ICategoryManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public CategoryManager(IGeneralEntityDao generalEntityDao,
                           IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    @Transactional
    public void saveCategory(CategoryDto categoryDto) {
        Category category = itemTransformService.mapCategoryDtoToCategory(categoryDto);
        generalEntityDao.persistEntity(category);
    }

    @Override
    @Transactional
    public void updateCategory(String regionCode, String categoryKey, CategoryDto categoryDto) {
        Region region = generalEntityDao.findEntity(
                "Region.findByCode", Pair.of(CODE, categoryDto.getRegion()));

        Optional<Category> existingCategoryOpt = generalEntityDao.findOptionEntity(
                "Category.findByKeyAndRegion",
                Arrays.asList(Pair.of(KEY, categoryKey), Pair.of(CODE, regionCode))
        );

        Category existingCategory = existingCategoryOpt
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setRegion(region);
        CategoryCascade categoryCascade = categoryDto.getCategory();

        Map<String, Category> registry = flattenByKey(existingCategory);

//        String incomingRootKey = ensureKey(categoryCascade.getKey(), categoryCascade.getValue());
        String incomingRootKey = ensureKey(categoryCascade.getKey());

        registry.putIfAbsent(existingCategory.getKey(), existingCategory);
        registry.putIfAbsent(incomingRootKey, existingCategory);

        applyCategoryUpdate(existingCategory, categoryCascade, region, registry);

        generalEntityDao.mergeEntity(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(String regionCode, String categoryKey) {
        Optional<Category> optionalCategory = generalEntityDao.findOptionEntity(
                "Category.findByKeyAndRegion",
                List.of(Pair.of(CODE, regionCode), Pair.of(KEY, categoryKey)));

        Category category = optionalCategory
                .orElseThrow(() -> new RuntimeException("Category not found"));
        generalEntityDao.removeEntity(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategory(String regionCode, String categoryKey) {
        Optional<Category> optionalCategory = generalEntityDao.findOptionEntity(
                "Category.findByKeyAndRegion",
                List.of(Pair.of(CODE, regionCode), Pair.of(KEY, categoryKey)));

        return optionalCategory
                .map(itemTransformService::mapCategoryToCategoryDto)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(String region) {
        List<Category> categories = generalEntityDao.findEntityList(
                "Category.findRootByRegion", Pair.of(CODE, region));

        return categories.stream()
                .map(itemTransformService::mapCategoryToCategoryDto)
                .toList();
    }

    private static String ensureKey(String key) {
        if (key != null && !key.trim().isEmpty()) return key.trim();
        return getUUID();
    }

    private Map<String, Category> flattenByKey(Category root) {
        Map<String, Category> map = new LinkedHashMap<>();
        Deque<Category> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Category c = stack.pop();
            if (c.getKey() != null) map.putIfAbsent(c.getKey(), c);
            if (c.getChildList() != null) {
                for (Category ch : c.getChildList()) stack.push(ch);
            }
        }
        return map;
    }

    private void applyCategoryUpdate(Category target, CategoryCascade source, Region region, Map<String, Category> registry) {
        if (source.getValue() != null) target.setValue(source.getValue());
        target.setRegion(region);

        List<CategoryCascade> incoming = Optional.ofNullable(source.getChildList()).orElseGet(Collections::emptyList);
        // local fast lookup for existing children under THIS parent
        Map<String, Category> currentChildrenByKey = target.getChildList().stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getKey() != null)
                .collect(Collectors.toMap(Category::getKey, Function.identity(), (a,b)->a, LinkedHashMap::new));

        Set<Category> matched = Collections.newSetFromMap(new IdentityHashMap<>());

        for (CategoryCascade childDto : incoming) {
            if (childDto == null) continue;

//            String key = ensureKey(childDto.getKey(), childDto.getValue());
            String key = ensureKey(childDto.getKey());

            Category child = registry.get(key);

            if (child == null) {
                // Create new
                child = buildTree(childDto, region, registry);
                target.addChildEntity(child);
                matched.add(child);
                continue;
            }

            // Child exists: ensure it belongs to same region
            child.setRegion(region);

            // If it has a different parent, move (re-parent) it
            if (child.getParent() != target) {
                Category oldParent = child.getParent();
                if (oldParent != null) {
                    oldParent.removeChildEntity(child);
                }
                target.addChildEntity(child);
            }

            // Update value & recurse
            if (childDto.getValue() != null) child.setValue(childDto.getValue());
            applyCategoryUpdate(child, childDto, region, registry);
            matched.add(child);
        }

        // remove orphans under THIS parent (not in matched)
        target.getChildList().stream()
                .filter(c -> !matched.contains(c))
                .toList()
                .forEach(target::removeChildEntity);
    }

    private Category buildTree(CategoryCascade dto, Region region, Map<String, Category> registry) {
//        String key = ensureKey(modal.getKey(), modal.getValue());
        String key = ensureKey(dto.getKey());

        Category cat = Category.builder()
                .key(key)
                .value(dto.getValue())
                .region(region)
                .build();

        registry.putIfAbsent(key, cat);

        List<CategoryCascade> children = Optional.ofNullable(dto.getChildList()).orElseGet(Collections::emptyList);
        for (CategoryCascade childDto : children) {
            Category child = buildTree(childDto, region, registry);
            cat.addChildEntity(child);
        }
        return cat;
    }

}
