package com.b2c.prototype.modal.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "OrderHistory.get",
        query = "SELECT o FROM OrderHistory o "
)
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;
//    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
//    @Builder.Default
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<CustomerSingleDeliveryOrder> customerOrders = new ArrayList<>();
}
