package com.b2c.prototype.transform.help.calculate;

import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Price;

public interface IPriceCalculationService {

    Price calculateCommissionPrice(MinMaxCommission minMaxCommission, Price totalPrice);

    Price calculateCurrentPrice(Price totalPrice, Discount orderDiscount, Price commissionPrice);
    Price calculateCommissionPrice(Price totalPrice, Discount orderDiscount, CommissionValue commissionValue);
}
