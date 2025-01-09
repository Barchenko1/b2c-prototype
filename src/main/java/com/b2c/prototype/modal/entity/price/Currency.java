package com.b2c.prototype.modal.entity.price;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "currency")
@SuperBuilder
@NoArgsConstructor
public class Currency extends AbstractConstantEntity {
}
