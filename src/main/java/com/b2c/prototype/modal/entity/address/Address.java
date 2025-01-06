package com.b2c.prototype.modal.entity.address;

import com.b2c.prototype.modal.base.AbstractAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "address")
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends AbstractAddress {

}
