package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.AbstractPayment;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.b2c.prototype.modal.entity.price.Price;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractPayment {
    private String paymentId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    @ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.MERGE)
    private CurrencyDiscount currencyDiscount;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private PercentDiscount percentDiscount;
}
