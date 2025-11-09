package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.entity.region.Region;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "discount_group")
@NamedQueries({

})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(unique = true, nullable = false)
    private String key;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;
    @OneToMany(mappedBy = "discountGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Discount> discounts = new HashSet<>();

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
        discount.setDiscountGroup(this);
    }

    public void removeDiscount(Discount discount) {
        this.discounts.remove(discount);
        discount.setDiscountGroup(null);
    }
}
