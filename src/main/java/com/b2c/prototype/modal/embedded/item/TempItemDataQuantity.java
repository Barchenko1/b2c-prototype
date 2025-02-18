package com.b2c.prototype.modal.embedded.item;

import com.b2c.prototype.modal.embedded.bucket.Bucket;
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
@Table(name = "temp_item_data_quantity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TempItemDataQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "temp_item_data_quantity_temp_item_data",
            joinColumns = {@JoinColumn(name = "temp_item_data_quantity_id")},
            inverseJoinColumns = {@JoinColumn(name = "temp_item_data_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<TempItemData> tempItemDataSet = new HashSet<>();
    private int quantity;

    @ManyToMany(mappedBy = "tempItemDataQuantitySet")
    @Builder.Default
    @ToString.Exclude
    private Set<Bucket> bucketSet = new HashSet<>();

}
