package com.b2c.prototype.modal.dto.common;

import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.payment.SellerCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class PaymentPrice {
    private List<ArticularItemQuantity> articularItemQuantityList;
    private Delivery delivery;
    private Discount discount;
    private SellerCommission sellerCommission;

    public Price calculateTotolOrderPrice() {
        Price totalArticularPriceQuantityPrice = calculateTotalArticularPriceQuantityPrice();
        Price discountPrice = calculateTotalDiscountPrice();
        Price commissionPrice = calculateCommissionTotolPrice(calculateTotalArticularPriceQuantityPrice());
        Price deliveryPrice = calculateTotalDeliveryPrice();


        double amount = totalArticularPriceQuantityPrice.getAmount() - discountPrice.getAmount() + deliveryPrice.getAmount();
        return null;
    }

    private Price calculateTotalDeliveryPrice() {
        return Price.builder()
                .amount(discount.getAmount())
                .currency(discount.getCurrency())
                .build();
    }

    private Price calculateCommissionTotolPrice(Price totalArticularPriceQuantityPrice) {
        if (sellerCommission.getFeeType().equals(FeeType.PERCENTAGE)) {
            return Price.builder()
                    .amount(totalArticularPriceQuantityPrice.getAmount() / 100 * sellerCommission.getAmount())
                    .currency(sellerCommission.getCurrency())
                    .build();
        } else {
            return Price.builder()
                    .amount(sellerCommission.getAmount())
                    .currency(sellerCommission.getCurrency())
                    .build();
        }
    }

    private Price calculateTotalDiscountPrice() {
        return Price.builder()
                .amount(discount.getAmount())
                .currency(discount.getCurrency())
                .build();
    }

    private Price calculateTotalArticularPriceQuantityPrice() {
        Map<Currency, Double> currencyAmountMap = articularItemQuantityList.stream()
                .collect(Collectors.toMap(
                        articularItemQuantity ->
                                articularItemQuantity.getArticularItem().getTotalPrice().getCurrency(),
                        articularItemQuantity ->
                                articularItemQuantity.getArticularItem().getTotalPrice().getAmount(),
                        Double::sum
                ));
        Price totalArticularPriceQuantity = currencyAmountMap.entrySet().stream()
                .map(entry -> Price.builder()
                        .currency(entry.getKey())
                        .amount(entry.getValue())
                        .build())
                .reduce((p1, p2) -> {
                    return Price.builder()
                            .amount(p1.getAmount() + p2.getAmount())
                            .currency(p1.getCurrency())
                            .build();
                }).orElse(null);

        return totalArticularPriceQuantity;
    }
}
