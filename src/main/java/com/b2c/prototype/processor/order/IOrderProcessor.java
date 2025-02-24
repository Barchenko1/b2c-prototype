package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderDetails;

import java.util.List;
import java.util.Map;

public interface IOrderProcessor {
    void saveOrderArticularItemQuantity(Map<String, String> requestParams, OrderArticularItemQuantityDto orderItemDataSearchFieldEntityDto);
    void updateOrderArticularItemQuantity(Map<String, String> requestParams, OrderArticularItemQuantityDto orderItemDataSearchFieldEntityDto);
    void deleteOrder(Map<String, String> requestParams);

    ResponseOrderDetails getResponseOrderDetails(Map<String, String> requestParams);
    List<ResponseOrderDetails> getResponseOrderDetailsList(Map<String, String> requestParams);
}
