package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.service.converter.ItemDataDescriptionConverter;
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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.b2c.prototype.util.Util.getUUID;

@Entity
@Table(name = "item_data")
@NamedEntityGraph(
        name = "itemData.full",
        attributeNodes = {
                @NamedAttributeNode(value = "category"),
                @NamedAttributeNode(value = "itemType"),
                @NamedAttributeNode(value = "brand"),
                @NamedAttributeNode(value = "articularItemSet", subgraph = "articularItem.subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "articularItem.subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("articularId"),
                                @NamedAttributeNode(value = "optionItems", subgraph = "optionItem.subgraph"),
                                @NamedAttributeNode(value = "fullPrice", subgraph = "price.subgraph"),
                                @NamedAttributeNode(value = "totalPrice", subgraph = "price.subgraph"),
                                @NamedAttributeNode(value = "status"),
                                @NamedAttributeNode(value ="discount", subgraph = "discount.subgraph")
                        }
                ),
                @NamedSubgraph(
                        name = "price.subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("currency")
                        }
                ),
                @NamedSubgraph(
                        name = "optionItem.subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("optionGroup"),
                        }
                ),
                @NamedSubgraph(
                        name = "discount.subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("currency")
                        }
                )
        }
)
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
