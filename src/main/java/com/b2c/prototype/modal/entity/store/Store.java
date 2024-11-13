package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.base.AbstractStore;
import com.b2c.prototype.modal.entity.option.OptionItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "option_item")
    private OptionItem optionItem;
}
