package com.b2c.prototype.service.base.payment;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IPaymentMethodService {
    void savePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updatePaymentMethod(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deletePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
