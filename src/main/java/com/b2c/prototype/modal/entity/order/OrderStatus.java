package com.b2c.prototype.modal.entity.order;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_status")
@SuperBuilder
@NoArgsConstructor
public class OrderStatus extends AbstractConstantEntity {
}
