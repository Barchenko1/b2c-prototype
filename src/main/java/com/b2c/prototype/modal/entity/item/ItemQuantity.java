package com.b2c.prototype.modal.entity.item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item_quantity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_quantity_item_data",
            joinColumns = {@JoinColumn(name = "item_quantity_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_data_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<ItemData> itemDataSet = new HashSet<>();
    private int quantity;
}
