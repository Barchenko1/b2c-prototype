package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
import com.b2c.prototype.modal.entity.price.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discount")
@NamedQueries({
        @NamedQuery(
                name = "Discount.all",
                query = "SELECT d FROM Discount d"
        ),
        @NamedQuery(
                name = "Discount.currency",
                query = "SELECT d FROM Discount d " +
                        "LEFT JOIN FETCH d.currency " +
                        "WHERE d.charSequenceCode = :charSequenceCode"
        ),
        @NamedQuery(
                name = "ArticularItem.findByDiscountCharSequenceCode",
                query = "SELECT ai FROM ArticularItem ai " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.articularItemList " +
                        "LEFT JOIN FETCH d.currency " +
                        "WHERE d.charSequenceCode = :charSequenceCode"
        ),
        @NamedQuery(
                name = "ArticularItem.findByDiscountNotNull",
                query = "SELECT ai FROM ArticularItem ai " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.articularItemList " +
                        "LEFT JOIN FETCH d.currency " +
                        "WHERE ai.discount IS NOT NULL"
        )
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "char_sequence_code", unique = true, nullable = false)
    private String charSequenceCode;
    private double amount;
    private boolean isActive;
    private boolean isPercent;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ArticularItem> articularItemList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "discount_group_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    protected DiscountGroup discountGroup;
}
