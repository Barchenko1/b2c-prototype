package com.b2c.prototype.modal.entity.order;

import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(
        @NamedQuery(
                name = "Order.findByOrderIdWithPayment",
                query = "SELECT c FROM CustomerOrder c " +
                        "LEFT JOIN FETCH c.payment p " +
                        "LEFT JOIN FETCH p.creditCard " +
                        "WHERE c.orderId = :orderId"
        )
)
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ContactInfo contactInfo;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_articular_item_quantity_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<DeliveryArticularItemQuantity> articularItemQuantityList = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private String note;
}
