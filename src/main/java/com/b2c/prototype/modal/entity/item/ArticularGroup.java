package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.entity.tenant.Tenant;
import com.b2c.prototype.transform.converter.ArticularGroupDescriptionConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "articular_group")
@NamedQueries({
        @NamedQuery(
                name = "ArticularGroup.full",
                query = "SELECT d FROM ArticularGroup d"
        ),
        @NamedQuery(
                name = "ArticularGroup.findByKey",
                query = "SELECT d FROM ArticularGroup d " +
                        "WHERE d.articularGroupId = :articularGroupId"
        ),
        @NamedQuery(
                name = "ArticularGroup.findByRegionAndKey",
                query = "SELECT ag FROM ArticularGroup ag " +
                        "LEFT JOIN FETCH ag.tenant agr " +
                        "LEFT JOIN FETCH agr.primaryCurrency agrc " +
                        "LEFT JOIN FETCH ag.items it " +
                        "LEFT JOIN FETCH it.articularItem ai " +
                        "LEFT JOIN FETCH ai.optionItems oi " +
                        "LEFT JOIN FETCH oi.optionGroup og " +
                        "LEFT JOIN FETCH ai.optionItemCosts oic " +
                        "LEFT JOIN FETCH oic.optionGroup ogc " +
                        "LEFT JOIN FETCH ai.fullPrice fp " +
                        "LEFT JOIN FETCH fp.currency fpc " +
                        "LEFT JOIN FETCH ai.totalPrice tp " +
                        "LEFT JOIN FETCH tp.currency tpc " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.discountGroup dg " +
                        "LEFT JOIN FETCH d.currency dc " +
                        "LEFT JOIN FETCH ag.category agc " +
                        "LEFT JOIN FETCH agc.childList agccl " +

                        "LEFT JOIN FETCH it.reviews itr " +
                        "LEFT JOIN FETCH it.posts itp " +

                        "WHERE agr.code = :code and ag.articularGroupId = :articularGroupId"
        ),
        @NamedQuery(
                name = "ArticularGroup.findAllWithFullRelations",
                query = "SELECT DISTINCT ag FROM ArticularGroup ag " +
                        "LEFT JOIN FETCH ag.items it " +
                        "LEFT JOIN FETCH it.articularItem ai " +
                        "LEFT JOIN FETCH ai.optionItems oi " +
                        "LEFT JOIN FETCH oi.optionGroup og " +
                        "LEFT JOIN FETCH ai.fullPrice fp " +
                        "LEFT JOIN FETCH fp.currency fpc " +
                        "LEFT JOIN FETCH ai.totalPrice tp " +
                        "LEFT JOIN FETCH tp.currency tpc " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.currency dc " +
                        "LEFT JOIN FETCH d.articularItemList dai"
        ),
        @NamedQuery(
                name = "ArticularGroup.findItemDataWithFullRelations",
                query = "SELECT DISTINCT ag FROM ArticularGroup ag " +
                        "LEFT JOIN FETCH ag.items it " +
                        "LEFT JOIN FETCH it.articularItem ai " +
                        "LEFT JOIN FETCH ai.optionItems oi " +
                        "LEFT JOIN FETCH oi.optionGroup og " +
                        "LEFT JOIN FETCH ai.fullPrice fp " +
                        "LEFT JOIN FETCH fp.currency fpc " +
                        "LEFT JOIN FETCH ai.totalPrice tp " +
                        "LEFT JOIN FETCH tp.currency tpc " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.currency dc " +
                        "LEFT JOIN FETCH d.articularItemList dai " +
                        "WHERE ag.articularGroupId = :articularGroupId"
        )
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticularGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "articular_group_uniq_id", unique = true, nullable = false)
    private String articularGroupId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @JoinColumn(name = "articular_group_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Item> items = new HashSet<>();
    @Column(name = "description", columnDefinition = "TEXT")
    @Convert(converter = ArticularGroupDescriptionConverter.class)
    @Builder.Default
    private Map<String, String> description = new LinkedHashMap<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
