package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.entity.price.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "currency_coefficient")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "CurrencyCoefficient.findByKey",
                query = "SELECT e FROM CurrencyCoefficient e " +
                        "LEFT JOIN FETCH e.currencyFrom ecf " +
                        "LEFT JOIN FETCH e.currencyTo ect " +
                        "WHERE e.dateOfCreate = : dateOfCreate"
        ),
        @NamedQuery(
                name = "CurrencyCoefficient.findAllByKey",
                query = "SELECT e FROM CurrencyCoefficient e " +
                        "LEFT JOIN FETCH e.currencyFrom ecf " +
                        "LEFT JOIN FETCH e.currencyTo ect "
        )
})
public class CurrencyCoefficient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currencyFrom;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currencyTo;
    @Column(nullable = false)
    private double coefficient;
    @Column(nullable = false, unique = true)
    private LocalDate dateOfCreate;
}
