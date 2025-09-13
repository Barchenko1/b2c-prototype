package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.constant.CountType;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articular_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticularStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "articular_item_quantity_id")
    @Builder.Default
    private List<ArticularItemQuantity> articularItemQuantities = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "articular_status_id")
    private AvailabilityStatus availabilityState;
    @Enumerated(EnumType.STRING)
    @Column(name = "count_type", nullable = false)
    private CountType countType;
}