package com.b2c.prototype.modal.entity.order;

import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.item.ItemQuantity;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.user.AppUser;
import com.b2c.prototype.modal.entity.user.UserInfo;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    //todo
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;
//    @ManyToMany
//    @JoinTable(
//            name = "order_middle_item",
//            joinColumns = { @JoinColumn(name = "order_item_id") },
//            inverseJoinColumns = { @JoinColumn(name = "item_id") }
//    )
//    private List<Item> itemList;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_quantity_item",
            joinColumns = {@JoinColumn(name = "order_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_quantity_id")}
    )
    private List<ItemQuantity> itemQuantityList;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Delivery delivery;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserInfo> userInfoList;
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderStatus orderStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;
    private String note;
}
