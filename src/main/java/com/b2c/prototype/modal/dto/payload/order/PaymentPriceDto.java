package com.b2c.prototype.modal.dto.payload.order;

import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.price.Price;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentPriceDto {
    private String paymentMethod;
    private CreditCardDto creditCard;
    private String discountCharSequenceCode;
    private Price fullPaymentPrice;
    private Price discountPrice;
    private Price totalPaymentPrice;
}
