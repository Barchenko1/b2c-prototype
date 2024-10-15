package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.entity.price.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency_discount")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String charSequenceCode;
    private int amount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;
}
