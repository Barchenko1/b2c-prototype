package com.b2c.prototype.modal.entity.delivery;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "delivery")
@Data
@SuperBuilder
@NoArgsConstructor
public class Delivery extends AbstractDelivery {
}
