package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.AbstractOneColumnEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payment_method")
@SuperBuilder
@NoArgsConstructor
public class PaymentMethod extends AbstractOneColumnEntity {
}
