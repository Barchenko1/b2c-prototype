package com.b2c.prototype.transform.constant;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;

public interface IConstantTransformService {
    Currency mapConstantPayloadDtoToCurrency(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapCurrencyToConstantPayloadDto(Currency currency);
}
