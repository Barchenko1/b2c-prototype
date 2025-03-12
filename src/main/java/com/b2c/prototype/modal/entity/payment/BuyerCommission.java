package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.commission.AbstractCommission;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "buyer_commission")
@Data
@SuperBuilder
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "BuyerCommission.getCommissionList",
                query = "SELECT b FROM BuyerCommission b " +
                        "LEFT JOIN FETCH b.currency c " +
                        "ORDER BY b.effectiveDate DESC"
        ),
        @NamedQuery(
                name = "BuyerCommission.getCommissionByEffectiveDate",
                query = "SELECT b FROM BuyerCommission b " +
                        "WHERE b.effectiveDate =: effectiveDate"
        )
})
public class BuyerCommission extends AbstractCommission {
}
