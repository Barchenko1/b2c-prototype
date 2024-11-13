package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractOneColumnEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "item_status")
@SuperBuilder
@NoArgsConstructor
public class ItemStatus extends AbstractOneColumnEntity {

}
