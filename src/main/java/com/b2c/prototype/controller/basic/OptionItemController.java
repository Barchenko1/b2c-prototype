package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.b2c.prototype.processor.option.IOptionItemProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/option")
public class OptionItemController {

    private final IOptionItemProcessor optionItemProcessor;

    public OptionItemController(IOptionItemProcessor optionItemProcessor) {
        this.optionItemProcessor = optionItemProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateOptionItem(@RequestParam final Map<String, String> requestParams,
                                                     @RequestBody final SingleOptionItemDto singleOptionItemDto) {
        optionItemProcessor.saveUpdateOptionItem(singleOptionItemDto, requestParams);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateOptionItemSet(@RequestParam final Map<String, String> requestParams,
                                                        @RequestBody final List<OptionItemDto> optionItemDtoList) {
        optionItemProcessor.saveOptionItemSet(optionItemDtoList, requestParams);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteOptionItem(@RequestParam final Map<String, String> requestParams) {
        optionItemProcessor.deleteOptionItem(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public OptionItemDto getOptionItem(@RequestParam final Map<String, String> requestParams) {

        return optionItemProcessor.getOptionItemDto(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptionItemDto> getOptionItemList(@RequestParam final Map<String, String> requestParams) {

        return optionItemProcessor.getOptionItemDtoList(requestParams);
    }

}
