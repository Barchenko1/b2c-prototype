package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payment_method")
@SuperBuilder
@NoArgsConstructor
public class PaymentMethod extends AbstractConstantEntity {
}
