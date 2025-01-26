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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
                name = "ItemDataOption.findByDiscountCharSequenceCode",
                query = "SELECT i FROM ItemDataOption i " +
                        "LEFT JOIN FETCH i.discount d " +
                        "LEFT JOIN FETCH d.currency " +
                        "WHERE d.charSequenceCode = :charSequenceCode"
        ),
        @NamedQuery(
                name = "ItemDataOption.findByDiscountNotNull",
                query = "SELECT i FROM ItemDataOption i " +
                        "LEFT JOIN FETCH i.discount d " +
                        "LEFT JOIN FETCH d.currency " +
                        "WHERE i.discount IS NOT NULL"
        )
})
@Data
@SuperBuilder
@NoArgsConstructor
public class Discount extends AbstractDiscount {
    private boolean isPercent;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Currency currency;
}
