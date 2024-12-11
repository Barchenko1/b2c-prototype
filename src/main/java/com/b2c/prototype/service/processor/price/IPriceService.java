package com.b2c.prototype.service.processor.price;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.request.PriceDtoSearchField;

import java.util.List;

public interface IPriceService {
    void saveFullPriceByOrderId(PriceDtoSearchField priceDtoSearchField);
    void saveTotalPriceByOrderId(PriceDtoSearchField priceDtoSearchField);
    void updateFullPriceByOrderId(PriceDtoSearchField priceDtoSearchField);
    void updateTotalPriceByOrderId(PriceDtoSearchField priceDtoSearchField);
    void deleteFullPriceByOrderId(OneFieldEntityDto oneFieldEntityDto);
    void deleteTotalPriceByOrderId(OneFieldEntityDto oneFieldEntityDto);

    void saveFullPriceByArticularId(PriceDtoSearchField priceDtoSearchField);
    void saveTotalPriceByArticularId(PriceDtoSearchField priceDtoSearchField);
    void updateFullPriceByArticularId(PriceDtoSearchField priceDtoSearchField);
    void updateTotalPriceByArticularId(PriceDtoSearchField priceDtoSearchField);
    void deleteFullPriceByArticularId(OneFieldEntityDto oneFieldEntityDto);
    void deleteTotalPriceByArticularId(OneFieldEntityDto oneFieldEntityDto);

    PriceDto getFullPriceByOrderId(OneFieldEntityDto oneFieldEntityDto);
    PriceDto getTotalPriceByOrderId(OneFieldEntityDto oneFieldEntityDto);

    PriceDto getFullPriceByArticularId(OneFieldEntityDto oneFieldEntityDto);
    PriceDto getTotalPriceByArticularId(OneFieldEntityDto oneFieldEntityDto);
    List<PriceDto> getPrices();
}
