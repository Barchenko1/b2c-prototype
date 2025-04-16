package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.commission.AbstractCommission;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "commission_value"
//        uniqueConstraints = @UniqueConstraint(columnNames = {"amount", "fee_type", "currency_id"})
)
@Data
@SuperBuilder
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
public class CommissionValue extends AbstractCommission {
}
