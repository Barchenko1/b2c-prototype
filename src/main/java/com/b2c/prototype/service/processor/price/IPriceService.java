package com.b2c.prototype.service.processor.price;

import com.b2c.prototype.modal.constant.PriceType;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.request.PriceDtoSearchField;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;

import java.util.List;

public interface IPriceService {
    void saveUpdatePriceByOrderId(PriceDtoSearchField priceDtoSearchField, PriceType priceType);
    void saveUpdatePriceByArticularId(PriceDtoSearchField priceDtoSearchField, PriceType priceType);
    void deletePriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceType priceType);
    void deletePriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceType priceType);

    PriceDto getPriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceType priceType);
    PriceDto getPriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceType priceType);
    ResponsePriceDto getResponsePriceDtoByArticularId(OneFieldEntityDto oneFieldEntityDto);
    ResponsePriceDto getResponsePriceDtoByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<PriceDto> getPrices();
}
