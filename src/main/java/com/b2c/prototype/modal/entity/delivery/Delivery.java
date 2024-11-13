package com.b2c.prototype.modal.entity.delivery;

import com.b2c.prototype.modal.base.AbstractDelivery;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "delivery")
@SuperBuilder
@NoArgsConstructor
public class Delivery extends AbstractDelivery {
}
