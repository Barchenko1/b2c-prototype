package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.AbstractCommission;
import jakarta.persistence.Entity;
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
@NamedQuery(
        name = "SellerCommission.getLatest",
        query = "SELECT s FROM SellerCommission s ORDER BY s.effectiveDate DESC"
)
public class SellerCommission extends AbstractCommission {
}
