package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.processor.item.ICategoryProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/item/category")
public class CategoryController {

    private final ICategoryProcess categoryProcess;

    public CategoryController(ICategoryProcess categoryProcess) {
        this.categoryProcess = categoryProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(@RequestParam final Map<String, String> requestParams,
                                               @RequestBody final CategoryDto categoryDto) {
        categoryProcess.createCategory(requestParams, categoryDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCategory(@RequestParam final Map<String, String> requestParams,
                                               @RequestBody final CategoryDto categoryDto) {
        categoryProcess.updateCategory(requestParams, categoryDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteCategory(@RequestParam final Map<String, String> requestParams) {
        categoryProcess.deleteCategory(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategory(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(categoryProcess.getCategory(requestParams), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> getCategories(@RequestParam final Map<String, String> requestParams) {
        return categoryProcess.getCategories(requestParams);
    }

}
