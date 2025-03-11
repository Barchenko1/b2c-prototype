package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.commission.AbstractCommission;
import jakarta.persistence.Entity;
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
@NamedQuery(
        name = "BuyerCommission.getLatest",
        query = "SELECT b FROM BuyerCommission b ORDER BY b.effectiveDate DESC"
)
public class BuyerCommission extends AbstractCommission {
}
