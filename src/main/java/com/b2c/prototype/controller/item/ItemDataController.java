package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.processor.item.IMetaDataProcessor;
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
@RequestMapping("/api/v1/itemdata")
public class ItemDataController {
    private final IMetaDataProcessor itemDataProcessor;

    public ItemDataController(IMetaDataProcessor itemDataProcessor) {
        this.itemDataProcessor = itemDataProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItemData(@RequestParam final Map<String, String> requestParams,
                                             @RequestBody final MetaDataDto metaDataDto) {
        itemDataProcessor.saveMetaData(requestParams, metaDataDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putItemData(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final MetaDataDto metaDataDto) {
        itemDataProcessor.updateMetaData(requestParams, metaDataDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchItemData(@RequestParam final Map<String, String> requestParams,
                                              @RequestBody final MetaDataDto metaDataDto) {
        itemDataProcessor.updateMetaData(requestParams, metaDataDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteItemData(@RequestParam final Map<String, String> requestParams) {
        itemDataProcessor.deleteMetaData(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseMetaDataDto> getItemDataList(@RequestParam final Map<String, String> requestParams) {

        return itemDataProcessor.getMetaDataList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMetaDataDto> getItemData(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(itemDataProcessor.getMetaData(requestParams), HttpStatus.OK);
    }
}
