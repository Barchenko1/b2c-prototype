package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.response.ResponseArticularItemDto;
import com.b2c.prototype.processor.item.IArticularItemProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articular")
public class ArticularItemController {
    private final IArticularItemProcessor articularItemProcessor;

    public ArticularItemController(IArticularItemProcessor articularItemProcessor) {
        this.articularItemProcessor = articularItemProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItemData(@RequestParam final Map<String, String> requestParams,
                                             @RequestBody final List<ArticularItemDto> itemDataDto) {
        articularItemProcessor.saveUpdateArticularItemList(requestParams, itemDataDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putItemData(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final List<ArticularItemDto> itemDataDto) {
        articularItemProcessor.saveUpdateArticularItemList(requestParams, itemDataDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchItemData(@RequestParam final Map<String, String> requestParams,
                                              @RequestBody final List<ArticularItemDto> itemDataDto) {
        articularItemProcessor.saveUpdateArticularItemList(requestParams, itemDataDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscount(@RequestParam final Map<String, String> requestParams) {
        articularItemProcessor.deleteArticularItem(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseArticularItemDto> getItemDataList(@RequestParam final Map<String, String> requestParams) {
        return articularItemProcessor.getResponseArticularItemDtoList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseArticularItemDto> getItemData(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(articularItemProcessor.getResponseArticularItemDto(requestParams), HttpStatus.OK);
    }


}
