package com.b2c.prototype.manager.price;

import com.b2c.prototype.modal.constant.PriceTypeEnum;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;

import java.util.List;

public interface IPriceManager {
    void saveUpdatePriceByOrderId(String orderId, PriceDto priceDto, PriceTypeEnum priceType);
    void saveUpdatePriceByArticularId(String ArticularId, PriceDto priceDto, PriceTypeEnum priceType);
    void deletePriceByOrderId(String orderId, PriceTypeEnum priceType);
    void deletePriceByArticularId(String articularId, PriceTypeEnum priceType);

    PriceDto getPriceByOrderId(String orderId, PriceTypeEnum priceType);
    PriceDto getPriceByArticularId(String articularId, PriceTypeEnum priceType);

    ResponsePriceDto getResponsePriceDtoByOrderId(String orderId);
    ResponsePriceDto getResponsePriceDtoByArticularId(String articularId);
    List<PriceDto> getPrices();
}
