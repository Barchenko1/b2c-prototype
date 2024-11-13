package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.AbstractOneColumnEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "option_group")
@SuperBuilder
@NoArgsConstructor
public class OptionGroup extends AbstractOneColumnEntity {

}
