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
@Table(name = "seller_commission")
@Data
@SuperBuilder
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "SellerCommission.getCommissionList",
                query = "SELECT s FROM SellerCommission s " +
                        "LEFT JOIN FETCH s.currency c " +
                        "ORDER BY s.effectiveDate DESC"
        ),
        @NamedQuery(
                name = "SellerCommission.getCommissionByEffectiveDate",
                query = "SELECT s FROM SellerCommission s " +
                        "WHERE s.effectiveDate =: effectiveDate"
        )
})
public class SellerCommission extends AbstractCommission {
}
