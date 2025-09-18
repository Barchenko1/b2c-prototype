package com.b2c.prototype.modal.entity.order;

import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
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
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_single_delivery_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "CustomerSingleDeliveryOrder.findByOrderIdWithPayment",
                query = "SELECT c FROM CustomerSingleDeliveryOrder c " +
                        "LEFT JOIN FETCH c.payment p " +
                        "LEFT JOIN FETCH p.creditCard " +
                        "WHERE c.orderUniqId = :orderUniqId"
        ),
        @NamedQuery(
                name = "CustomerSingleDeliveryOrder.all",
                query = "SELECT c FROM CustomerSingleDeliveryOrder c " +
                        "LEFT JOIN FETCH c.payment p " +
                        "LEFT JOIN FETCH p.creditCard "
        )
})
public class CustomerSingleDeliveryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "order_uniq_id", unique = true, nullable = false)
    private String orderUniqId;
    private LocalDateTime dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id")
    private OrderStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiary_id")
    private ContactInfo beneficiary;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_single_delivery_order_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<ArticularItemQuantity> articularItemQuantities = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
    private String note;

    @ManyToOne
    @JoinColumn(name = "customer_single_delivery_order_id")
    @ToString.Exclude
    private CustomerSingleDeliveryOrder original;
}
