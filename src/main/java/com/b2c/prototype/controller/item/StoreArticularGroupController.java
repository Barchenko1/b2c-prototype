package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;
import com.b2c.prototype.processor.item.IStoreArticularGroupProcessor;
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
@RequestMapping("/api/v1/item/store/group")
public class StoreArticularGroupController {
    private final IStoreArticularGroupProcessor storeArticularGroupProcessor;

    public StoreArticularGroupController(IStoreArticularGroupProcessor storeArticularGroupProcessor) {
        this.storeArticularGroupProcessor = storeArticularGroupProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItemData(@RequestParam final Map<String, String> requestParams,
                                             @RequestBody final StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        storeArticularGroupProcessor.saveStoreArticularGroup(requestParams, storeArticularGroupRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putItemData(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        storeArticularGroupProcessor.updateStoreArticularGroup(requestParams, storeArticularGroupRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteItemData(@RequestParam final Map<String, String> requestParams) {
        storeArticularGroupProcessor.deleteStoreArticularGroup(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StoreArticularGroupResponseDto> getItemDataList(@RequestParam final Map<String, String> requestParams) {

        return storeArticularGroupProcessor.getStoreArticularGroupList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StoreArticularGroupResponseDto> getItemData(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(storeArticularGroupProcessor.getStoreArticularGroup(requestParams), HttpStatus.OK);
    }
}
