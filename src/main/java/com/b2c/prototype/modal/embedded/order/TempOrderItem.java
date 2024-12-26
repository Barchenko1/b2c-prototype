package com.b2c.prototype.modal.embedded.order;

import com.b2c.prototype.modal.embedded.item.TempItemDataQuantity;
import com.b2c.prototype.modal.embedded.user.TempUserProfile;
import com.b2c.prototype.modal.base.AbstractOrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_data_item")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TempOrderItem extends AbstractOrderItem {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "temp_user_profile_id")
    private TempUserProfile tempUserProfile;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "temp_order_item_temp_item_data_quantity",
            joinColumns = {@JoinColumn(name = "temp_order_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "temp_item_data_quantity_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<TempItemDataQuantity> tempItemDataQuantitySet = new HashSet<>();
}
