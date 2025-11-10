package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.entity.price.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(
        name = "commission_value"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "CommissionValue.findByAmountAndFeeTypeAndCurrency",
                query = "SELECT c FROM CommissionValue c " +
                        "WHERE c.amount = :amount " +
                        "AND c.feeType = :feeType " +
                        "AND c.currency = :currency"
        )
})
public class CommissionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false)
    private FeeType feeType;
}
