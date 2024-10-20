package com.b2c.prototype.modal.entity.delivery;

import com.b2c.prototype.modal.entity.address.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private DeliveryType deliveryType;
}
