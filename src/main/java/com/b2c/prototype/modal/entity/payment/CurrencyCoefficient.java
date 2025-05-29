package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.entity.price.Currency;
import jakarta.persistence.CascadeType;
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

import java.util.Date;

@Entity
@Table(name = "currency_coefficient")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyCoefficient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Currency fromCurrency;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Currency toCurrency;
    @Column(nullable = false)
    private double coefficient;
    private Date dateOfCreate;
}
