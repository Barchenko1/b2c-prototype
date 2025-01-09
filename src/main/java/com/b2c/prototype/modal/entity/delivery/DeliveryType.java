package com.b2c.prototype.modal.entity.delivery;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "delivery_type")
@SuperBuilder
@NoArgsConstructor
public class DeliveryType extends AbstractConstantEntity {
}
