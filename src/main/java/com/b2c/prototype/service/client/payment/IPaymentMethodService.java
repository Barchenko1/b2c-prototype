package com.b2c.prototype.service.client.payment;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IPaymentMethodService {
    void savePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updatePaymentMethod(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deletePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
