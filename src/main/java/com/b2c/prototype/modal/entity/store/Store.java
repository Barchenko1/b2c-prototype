package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.base.store.AbstractStore;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "articular_item_id")
    private ArticularItem articularItem;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;
}
