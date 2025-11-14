package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.processor.item.IArticularGroupProcessor;
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
@RequestMapping("/api/v1/item/articular/group")
public class ArticularGroupController {
    private final IArticularGroupProcessor articularGroupProcessor;

    public ArticularGroupController(IArticularGroupProcessor articularGroupProcessor) {
        this.articularGroupProcessor = articularGroupProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItemData(@RequestParam final Map<String, String> requestParams,
                                             @RequestBody final ArticularGroupDto articularGroupDto) {
        articularGroupProcessor.saveArticularGroup(requestParams, articularGroupDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putItemData(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final ArticularGroupDto articularGroupDto) {
        articularGroupProcessor.updateArticularGroup(requestParams, articularGroupDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteItemData(@RequestParam final Map<String, String> requestParams) {
        articularGroupProcessor.deleteArticularGroup(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticularGroupDto> getItemDataList(@RequestParam final Map<String, String> requestParams) {

        return articularGroupProcessor.getArticularGroupList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticularGroupDto> getItemData(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(articularGroupProcessor.getArticularGroup(requestParams), HttpStatus.OK);
    }
}
