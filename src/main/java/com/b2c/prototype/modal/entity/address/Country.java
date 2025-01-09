package com.b2c.prototype.modal.entity.address;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "country")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Country extends AbstractConstantEntity {
    private String flagImagePath;
}
