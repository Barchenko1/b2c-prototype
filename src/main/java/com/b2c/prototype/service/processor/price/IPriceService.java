package com.b2c.prototype.service.processor.price;

import com.b2c.prototype.modal.constant.PriceTypeEnum;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.searchfield.PriceSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;

import java.util.List;

public interface IPriceService {
    void saveUpdatePriceByOrderId(PriceSearchFieldEntityDto priceSearchFieldEntityDto, PriceTypeEnum priceType);
    void saveUpdatePriceByArticularId(PriceSearchFieldEntityDto priceSearchFieldEntityDto, PriceTypeEnum priceType);
    void deletePriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType);
    void deletePriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType);

    PriceDto getPriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType);
    PriceDto getPriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType);
    ResponsePriceDto getResponsePriceDtoByArticularId(OneFieldEntityDto oneFieldEntityDto);
    ResponsePriceDto getResponsePriceDtoByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<PriceDto> getPrices();
}
