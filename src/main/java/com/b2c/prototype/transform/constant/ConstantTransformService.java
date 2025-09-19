package com.b2c.prototype.transform.constant;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;
import org.springframework.stereotype.Service;

@Service
public class ConstantTransformService implements IConstantTransformService {
    @Override
    public Currency mapConstantPayloadDtoToCurrency(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapCurrencyToConstantPayloadDto(Currency currency) {
        return null;
    }
}
