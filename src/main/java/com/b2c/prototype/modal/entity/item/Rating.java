package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractNumberConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rating")
@SuperBuilder
@NoArgsConstructor
public class Rating extends AbstractNumberConstantEntity {

}
