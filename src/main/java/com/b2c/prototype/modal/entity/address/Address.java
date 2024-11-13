package com.b2c.prototype.modal.entity.address;

import com.b2c.prototype.modal.base.AbstractAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "address")
@SuperBuilder
@NoArgsConstructor
public class Address extends AbstractAddress {

}
