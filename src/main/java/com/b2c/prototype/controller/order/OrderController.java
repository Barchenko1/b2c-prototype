package com.b2c.prototype.controller.order;

import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderDetails;
import com.b2c.prototype.processor.order.IOrderProcessor;
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
@RequestMapping("/api/v1/order")
public class OrderController {
    private final IOrderProcessor orderProcessor;

    public OrderController(IOrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItemData(@RequestParam final Map<String, String> requestParams,
                                             @RequestBody final OrderArticularItemQuantityDto orderArticularItemQuantityDto) {
        orderProcessor.saveOrderArticularItemQuantity(requestParams, orderArticularItemQuantityDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putItemData(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final OrderArticularItemQuantityDto orderArticularItemQuantityDto) {
        orderProcessor.updateOrderArticularItemQuantity(requestParams, orderArticularItemQuantityDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchItemData(@RequestParam final Map<String, String> requestParams,
                                              @RequestBody final OrderArticularItemQuantityDto orderArticularItemQuantityDto) {
        orderProcessor.updateOrderArticularItemQuantity(requestParams, orderArticularItemQuantityDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscount(@RequestParam final Map<String, String> requestParams) {
        orderProcessor.deleteOrder(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseOrderDetails> getItemDataList(@RequestParam final Map<String, String> requestParams) {
        return orderProcessor.getResponseOrderDetailsList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseOrderDetails> getItemData(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(orderProcessor.getResponseOrderDetails(requestParams), HttpStatus.OK);
    }
}
