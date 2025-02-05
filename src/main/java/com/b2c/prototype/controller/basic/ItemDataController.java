package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.processor.item.IItemDataProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itemdata")
public class ItemDataController {
    private final IItemDataProcessor itemDataProcessor;

    public ItemDataController(IItemDataProcessor itemDataProcessor) {
        this.itemDataProcessor = itemDataProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveDiscount(@RequestBody final ItemDataDto itemDataDto) {
        itemDataProcessor.saveItemData(itemDataDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putDiscountByCharSequenceCode(@RequestBody final ItemDataDto itemDataDto,
                                                              @RequestParam(value = "itemId") final String itemId) {
        itemDataProcessor.updateItemData(itemId, itemDataDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchDiscountByArticularId(@RequestBody final ItemDataDto itemDataDto,
                                                           @RequestParam(value = "itemId") final String itemId) {
        itemDataProcessor.updateItemData(itemId, itemDataDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscount(@RequestParam(value = "itemId") final String itemId) {

        itemDataProcessor.deleteItemData(itemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/all")
    public List<ResponseItemDataDto> getItemDataList(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {

        return itemDataProcessor.getItemDataList();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseItemDataDto> getItemData(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                                           @RequestParam(value = "itemId") final String itemId) {
        return new ResponseEntity<>(itemDataProcessor.getItemData(itemId), HttpStatus.OK);
    }
}
