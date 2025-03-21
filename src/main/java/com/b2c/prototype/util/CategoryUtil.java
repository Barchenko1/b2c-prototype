package com.b2c.prototype.util;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.entity.item.Category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CategoryUtil {

    public static Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }

        Category category = Category.builder()
                .label(dto.getLabel())
                .value(dto.getValue())
                .childList(new ArrayList<>())
                .build();

        if (dto.getChildList() != null) {
            for (CategoryDto childDto : dto.getChildList()) {
                Category childEntity = toEntity(childDto);
                category.addChildEntity(childEntity);
            }
        }

        return category;
    }

    public static CategoryDto toDto(Category entity) {
        if (entity == null) {
            return null;
        }
        return CategoryDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .oldValue(entity.getValue())
                .childList(entity.getChildList() != null
                        ? entity.getChildList().stream()
                        .map(CategoryUtil::toDto)
                        .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public static List<String> getValues(Category category) {
        return flattenCategory(category)
                .toList();
    }

    public static List<String> getAllValues(List<Category> categories) {
        return categories.stream()
                .flatMap(CategoryUtil::flattenCategory)
                .collect(Collectors.toList());
    }

    private static Stream<String> flattenCategory(Category category) {
        return Stream.concat(
                Stream.of(category.getValue()),
                category.getChildList() == null ? Stream.empty() :
                        category.getChildList().stream().flatMap(CategoryUtil::flattenCategory)
        );
    }

    private static Stream<String> flattenCategoryDto(CategoryDto categoryDto) {
        return Stream.concat(
                Stream.of(categoryDto.getValue()),
                categoryDto.getChildList() == null ? Stream.empty() :
                        categoryDto.getChildList().stream().flatMap(CategoryUtil::flattenCategoryDto)
        );
    }

    public static List<CategoryDto> flattenCategory(CategoryDto categoryDto) {
        List<CategoryDto> flatList = new ArrayList<>();
        extractNestedCategories(categoryDto, flatList);
        return flatList;
    }

    public static List<CategoryDto> flattenCategories(List<CategoryDto> categories) {
        List<CategoryDto> flatList = new ArrayList<>();
        for (CategoryDto category : categories) {
            extractNestedCategories(category, flatList);
        }
        return flatList;
    }

    private static void extractNestedCategories(CategoryDto category, List<CategoryDto> flatList) {
        if (category == null) return;

        flatList.add(category);

        if (category.getChildList() != null) {
            for (CategoryDto child : category.getChildList()) {
                extractNestedCategories(child, flatList);
            }
        }
    }

    public static List<String> validateNoDuplicates(List<String> existingValues, List<CategoryDto> categoryDtoList) {
        Set<String> duplicateValues = new HashSet<>();

        for (CategoryDto dto : categoryDtoList) {
            if (!Objects.equals(dto.getOldValue(), dto.getValue()) && existingValues.contains(dto.getValue())) {
                duplicateValues.add(dto.getValue());
            }
        }

        return new ArrayList<>(duplicateValues);
    }

}
