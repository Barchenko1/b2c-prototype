package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractDiscount;
import com.b2c.prototype.modal.entity.price.Currency;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "currency_discount")
@Data
@SuperBuilder
@NoArgsConstructor
public class CurrencyDiscount extends AbstractDiscount {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Currency currency;
}
