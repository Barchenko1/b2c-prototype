package com.b2c.prototype.util;

import com.b2c.prototype.configuration.TransitiveSelfYaml;
import com.b2c.prototype.modal.entity.item.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class CategoryUtil {
    public static Category buildCategory(TransitiveSelfYaml transitiveSelfYaml) {
        Category category = Category.builder()
                .name(transitiveSelfYaml.getName())
                .build();

        if (transitiveSelfYaml.getSub() != null) {
            for (TransitiveSelfYaml sub : transitiveSelfYaml.getSub()) {
                Category childCategory = buildCategory(sub);
                category.addChildTransitiveEntity(childCategory);
            }
        }

        return category;
    }

    public static List<TransitiveSelfYaml> extractTransitiveSelfYaml(Set<TransitiveSelfYaml> categories) {
        List<TransitiveSelfYaml> result = new ArrayList<>();
        for (TransitiveSelfYaml category : categories) {
            extractNamesRecursive(category, result);
        }
        return result;
    }

    private static void extractNamesRecursive(TransitiveSelfYaml category, List<TransitiveSelfYaml> transitiveSelfYamlList) {
        transitiveSelfYamlList.add(category);

        if (category.getSub() != null && !category.getSub().isEmpty()) {
            for (TransitiveSelfYaml subCategory : category.getSub()) {
                extractNamesRecursive(subCategory, transitiveSelfYamlList);
            }
        }
    }

    public static List<TransitiveSelfYaml> findNonExistingCategories(List<TransitiveSelfYaml> configYamlList, List<Category> existCategoryList) {
        Set<String> existingCategoryNames = existCategoryList.stream()
                .map(Category::getRootField)
                .collect(Collectors.toSet());

        return configYamlList.stream()
                .filter(configYaml -> !existingCategoryNames.contains(configYaml.getName()))
                .collect(Collectors.toList());
    }

    public static Map<Category, List<TransitiveSelfYaml>> findNonExistingCategoriesMap(
            Set<TransitiveSelfYaml> configYamlCategories,
            List<Category> existCategoryList
    ) {
        Map<Category, List<TransitiveSelfYaml>> resultMap = new HashMap<>();

        Map<String, Category> existingCategoriesByName = existCategoryList.stream()
                .collect(Collectors.toMap(Category::getRootField, category -> category));

        for (TransitiveSelfYaml yamlCategory : configYamlCategories) {
            findNewSubcategories(yamlCategory, existingCategoriesByName, resultMap);
        }

        return resultMap;
    }

    private static void findNewSubcategories(
            TransitiveSelfYaml yamlCategory,
            Map<String, Category> existingCategoriesByName,
            Map<Category, List<TransitiveSelfYaml>> resultMap
    ) {
        Category existingParentCategory = existingCategoriesByName.get(yamlCategory.getName());

        if (existingParentCategory != null) {
            List<TransitiveSelfYaml> newSubcategories;
            if (yamlCategory.getSub() != null && !yamlCategory.getSub().isEmpty()) {
                newSubcategories = yamlCategory.getSub().stream()
                        .filter(subName -> existingParentCategory.getChildNodeList().stream()
                                .noneMatch(child -> child.getName().equals(subName.getName())))
                        .collect(Collectors.toList());

                if (!newSubcategories.isEmpty()) {
                    resultMap.put(existingParentCategory, newSubcategories);
                }

                for (TransitiveSelfYaml subYaml : yamlCategory.getSub()) {
                    findNewSubcategories(subYaml, existingCategoriesByName, resultMap);
                }
            }
        }
    }
}
