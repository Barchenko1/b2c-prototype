package com.b2c.prototype.util;

import com.b2c.prototype.configuration.modal.TransitiveSelfYaml;
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
                .value(transitiveSelfYaml.getName())
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
}
