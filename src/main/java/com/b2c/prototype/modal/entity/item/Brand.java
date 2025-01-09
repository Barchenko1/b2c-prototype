package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "brand")
@SuperBuilder
@NoArgsConstructor
public class Brand extends AbstractConstantEntity {

}
