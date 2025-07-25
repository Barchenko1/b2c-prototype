package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.transform.converter.ItemDataDescriptionConverter;
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
@Table(name = "item_data")
@NamedQueries({
        @NamedQuery(
                name = "ItemData.full",
                query = "SELECT d FROM ItemData d"
        ),
        @NamedQuery(
                name = "ItemData.findByValue",
                query = "SELECT d FROM ItemData d WHERE d.itemId = :itemId"
        ),
        @NamedQuery(
                name = "ItemData.findAllWithFullRelations",
                query = "SELECT DISTINCT id FROM ItemData id " +
                        "LEFT JOIN FETCH id.category c " +
                        "LEFT JOIN FETCH id.itemType it " +
                        "LEFT JOIN FETCH id.brand b " +
                        "LEFT JOIN FETCH id.articularItemSet ai " +
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
                name = "ItemData.findItemDataWithFullRelations",
                query = "SELECT DISTINCT id FROM ItemData id " +
                        "LEFT JOIN FETCH id.category c " +
                        "LEFT JOIN FETCH id.itemType it " +
                        "LEFT JOIN FETCH id.brand b " +
                        "LEFT JOIN FETCH id.articularItemSet ai " +
                        "LEFT JOIN FETCH ai.optionItems oi " +
                        "LEFT JOIN FETCH oi.optionGroup og " +
                        "LEFT JOIN FETCH ai.fullPrice fp " +
                        "LEFT JOIN FETCH fp.currency fpc " +
                        "LEFT JOIN FETCH ai.totalPrice tp " +
                        "LEFT JOIN FETCH tp.currency tpc " +
                        "LEFT JOIN FETCH ai.discount d " +
                        "LEFT JOIN FETCH d.currency dc " +
                        "LEFT JOIN FETCH d.articularItemList dai " +
                        "WHERE id.itemId = :itemId"
        )
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "item_id", unique = true, nullable = false)
    private String itemId;
    @Column(name = "description", columnDefinition = "TEXT")
    @Convert(converter = ItemDataDescriptionConverter.class)
    @Builder.Default
    private Map<String, String> description = new LinkedHashMap<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ItemType itemType;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "item_data_id")
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<ArticularItem> articularItemSet = new HashSet<>();
}
