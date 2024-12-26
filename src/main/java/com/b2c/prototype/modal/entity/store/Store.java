package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.base.AbstractStore;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "store")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Store extends AbstractStore {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ItemDataOption itemDataOption;
}
