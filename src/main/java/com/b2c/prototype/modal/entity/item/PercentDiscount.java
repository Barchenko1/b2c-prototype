package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractDiscount;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "percent_discount")
@Data
@SuperBuilder
@NoArgsConstructor
public class PercentDiscount extends AbstractDiscount {
}
