package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractDiscount;
import com.b2c.prototype.modal.entity.price.Currency;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "discount")
@NamedEntityGraph(
        name = "discount.currency",
        attributeNodes = {
                @NamedAttributeNode("currency")
        }
)
@NamedQueries({
        @NamedQuery(
                name = "ArticularItem.findByDiscountCharSequenceCode",
                query = "SELECT ai FROM ArticularItem ai " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.currency " +
                        "WHERE d.charSequenceCode = :charSequenceCode"
        ),
        @NamedQuery(
                name = "ArticularItem.findByDiscountNotNull",
                query = "SELECT ai FROM ArticularItem ai " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.currency " +
                        "WHERE ai.discount IS NOT NULL"
        )
})
@Data
@SuperBuilder
@NoArgsConstructor
public class Discount extends AbstractDiscount {
    private boolean isPercent;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Currency currency;
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @Builder.Default
    private List<ArticularItem> articularItemList = new ArrayList<>();
}
