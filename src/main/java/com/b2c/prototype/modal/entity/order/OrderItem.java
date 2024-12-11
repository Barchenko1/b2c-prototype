package com.b2c.prototype.modal.entity.order;

import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.item.ItemDataQuantity;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserProfile;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "order_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private UserProfile userProfile;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_quantity_item",
            joinColumns = {@JoinColumn(name = "order_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_quantity_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<ItemDataQuantity> itemDataQuantitySet = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Delivery delivery;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Builder.Default
    private List<ContactInfo> beneficiaries = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private OrderStatus orderStatus;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Payment payment;
    private String note;
}
