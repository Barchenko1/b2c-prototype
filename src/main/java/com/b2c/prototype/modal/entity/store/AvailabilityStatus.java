package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "availability_status")
@SuperBuilder
@NoArgsConstructor
public class AvailabilityStatus extends AbstractConstantEntity {
}
