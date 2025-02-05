package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.service.converter.MapToJsonConverter;
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
import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@Entity
@Table(name = "item_data")
@NamedEntityGraph(
        name = "ItemData.full",
        attributeNodes = {
                @NamedAttributeNode(value = "category"),
                @NamedAttributeNode(value = "itemType"),
                @NamedAttributeNode(value = "brand"),
                @NamedAttributeNode(value = "status"),
                @NamedAttributeNode(value = "articularItemList", subgraph = "itemDataOptionListSubgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "itemDataOptionListSubgraph",
                        attributeNodes = {
//                                @NamedAttributeNode("someAttributeInItemDataOption") // Replace with actual attributes of ArticularItem
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
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ItemType itemType;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ItemStatus status;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "articular_item_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<ArticularItem> articularItemList = new ArrayList<>();

    @PrePersist
    protected void onPrePersist() {
        if (this.itemId == null) {
            this.itemId = getUUID();
        }
    }
}
