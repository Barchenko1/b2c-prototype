package com.b2c.prototype.transform.help.calculate;

import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Price;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculationService implements IPriceCalculationService {

    @Override
    public Price calculateCommissionPrice(MinMaxCommission minMaxCommission, Price totalPrice) {
        CommissionValue commissionValue = getCommissionValue(minMaxCommission, totalPrice);
        if (FeeType.FIXED.equals(commissionValue.getFeeType())) {
            return Price.builder()
                    .amount(commissionValue.getAmount())
                    .currency(commissionValue.getCurrency())
                    .build();
        } else {
            double calculatePercentAmount = (totalPrice.getAmount() / 100) * commissionValue.getAmount();
            return Price.builder()
                    .amount(calculatePercentAmount)
                    .currency(totalPrice.getCurrency())
                    .build();
        }
    }

    @Override
    public Price calculateCurrentPrice(Price totalPrice, Discount orderDiscount, Price commissionPrice) {
        if (orderDiscount != null) {
            return calculateTotalPrice(totalPrice, orderDiscount, commissionPrice);
        } else {
            return calculateTotalPrice(totalPrice, commissionPrice);
        }
    }

    @Override
    public Price calculateCommissionPrice(Price totalPrice, Discount orderDiscount, CommissionValue commissionValue) {
        if (orderDiscount != null) {
            return calculateSellerPriceDiscount(totalPrice, orderDiscount, commissionValue);
        } else {
            return calculateSellerPrice(totalPrice, commissionValue);
        }
    }

    private Price calculateTotalPrice(Price totalPrice, Discount orderDiscount, Price commissionPrice) {
        double totalAmount = 0;
        if (totalPrice.getCurrency().equals(orderDiscount.getCurrency())
                && orderDiscount.getCurrency().equals(commissionPrice.getCurrency())) {
            totalAmount = totalPrice.getAmount() - orderDiscount.getAmount() - commissionPrice.getAmount();
            if (totalAmount < 0) {
                throw new IllegalArgumentException("Total price cannot be negative");
            }
        }
        return Price.builder()
                .currency(totalPrice.getCurrency())
                .amount(totalAmount)
                .build();
    }

    private Price calculateTotalPrice(Price totalPrice, Price commissionPrice) {
        double totalAmount = 0;
        if (totalPrice.getCurrency().equals(commissionPrice.getCurrency())) {
            totalAmount = totalPrice.getAmount() - commissionPrice.getAmount();
            if (totalAmount < 0) {
                throw new IllegalArgumentException("Total price cannot be negative");
            }
        }
        return Price.builder()
                .currency(totalPrice.getCurrency())
                .amount(totalAmount)
                .build();
    }

    private Price calculateSellerPriceDiscount(Price totalPrice, Discount orderDiscount, CommissionValue commissionValue) {
        String totalPriceCurrency = totalPrice.getCurrency().getKey();
        String sellerCommissionCurrency = commissionValue.getCurrency().getKey();
        String orderDiscountCurrency = orderDiscount.getCurrency().getKey();
        double sellerCommissionAmount = 0;
        if (totalPriceCurrency.equals(sellerCommissionCurrency) && sellerCommissionCurrency.equals(orderDiscountCurrency)) {
            sellerCommissionAmount = ((totalPrice.getAmount() - orderDiscount.getAmount()) / 100) * commissionValue.getAmount();
            if (sellerCommissionAmount < 0) {
                throw new IllegalArgumentException("Total price cannot be negative");
            }
        }
        return Price.builder()
                .currency(commissionValue.getCurrency())
                .amount(sellerCommissionAmount)
                .build();
    }

    private Price calculateSellerPrice(Price totalPrice, CommissionValue commissionValue) {
        String totalPriceCurrency = totalPrice.getCurrency().getKey();
        String sellerCommissionCurrency = commissionValue.getCurrency().getKey();
        double sellerCommissionAmount = 0;
        if (totalPriceCurrency.equals(sellerCommissionCurrency)) {
            sellerCommissionAmount = (totalPrice.getAmount() / 100) * commissionValue.getAmount();
            if (sellerCommissionAmount < 0) {
                throw new IllegalArgumentException("Total price cannot be negative");
            }
        }
        return Price.builder()
                .currency(commissionValue.getCurrency())
                .amount(sellerCommissionAmount)
                .build();
    }

    private CommissionValue getCommissionValue(MinMaxCommission minMaxCommission, Price totalPrice) {
        Price changeCommissionPrice = minMaxCommission.getChangeCommissionPrice();
        if (!changeCommissionPrice.getCurrency().equals(totalPrice.getCurrency())) {
            throw new IllegalArgumentException("Currency does not match");
        }
        if (changeCommissionPrice.getAmount() < totalPrice.getAmount()) {
            return minMaxCommission.getMaxCommission();
        } else {
            return minMaxCommission.getMinCommission();
        }
    }

}
