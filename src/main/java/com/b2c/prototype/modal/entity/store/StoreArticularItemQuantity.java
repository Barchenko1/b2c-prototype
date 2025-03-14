package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.AvailabilityStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_articular_item_quantity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreArticularItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ArticularItemQuantity articularItemQuantity;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "availability_status_id")
    private AvailabilityStatus availabilityStatus;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "count_type_id")
    private CountType countType;
}
