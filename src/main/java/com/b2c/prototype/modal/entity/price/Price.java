package com.b2c.prototype.modal.entity.price;

import com.b2c.prototype.modal.entity.item.ArticularItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private double amount;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Currency currency;
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    private ArticularItem articularItem;
}
