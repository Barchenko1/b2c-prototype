package com.b2c.prototype.modal.base;

import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AbstractOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
//    @Column(name = "order_id", unique = true, nullable = false)
//    private String orderId;
    private long dateOfCreate;
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//    @JoinColumn(name = "user_profile_id")
//    private UserProfile userProfile;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "order_quantity_item",
//            joinColumns = {@JoinColumn(name = "order_item_id")},
//            inverseJoinColumns = {@JoinColumn(name = "item_quantity_id")}
//    )
//    @Builder.Default
//    @EqualsAndHashCode.Exclude
//    private Set<ItemQuantity> itemQuantitySet = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_item_contact_info",
            joinColumns = {@JoinColumn(name = "order_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "contact_info_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<ContactInfo> beneficiaries = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private String note;
}
