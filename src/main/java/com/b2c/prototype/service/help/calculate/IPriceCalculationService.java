package com.b2c.prototype.service.help.calculate;

import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.price.Price;

public interface IPriceCalculationService {

    Price calculateCurrentPrice(Price totalPrice, Discount orderDiscount, Price commissionPrice);
    Price calculateCommissionPrice(Price totalPrice, Discount orderDiscount, CommissionValue commissionValue);
}
