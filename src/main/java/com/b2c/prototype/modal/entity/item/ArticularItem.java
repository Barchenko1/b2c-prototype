package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Price;
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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static com.b2c.prototype.util.Util.getUUID;

@Entity
@Table(name = "articular_item")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "articularItem.discount.currency",
                attributeNodes = {
                        @NamedAttributeNode(value = "discount", subgraph = "discount.subgraph")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "discount.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode("currency")
                                }
                        )
                }
        ),
        @NamedEntityGraph(
                name = "articularItem.optionItems",
                attributeNodes = {
                        @NamedAttributeNode(value = "optionItems", subgraph = "optionItem.subgraph")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "optionItem.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode("optionGroup")
                                }
                        )
                }
        ),
        @NamedEntityGraph(
                name = "articularItem.full",
                attributeNodes = {
                        @NamedAttributeNode(value = "optionItems", subgraph = "optionItem.subgraph"),
                        @NamedAttributeNode(value = "fullPrice", subgraph = "price.subgraph"),
                        @NamedAttributeNode(value = "totalPrice", subgraph = "price.subgraph"),
                        @NamedAttributeNode(value = "discount", subgraph = "discount.subgraph")
                },
                subgraphs = {
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
//                                        @NamedAttributeNode("articularItems")
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
})
@NamedQueries({
        @NamedQuery(
                name = "ArticularItem.findByOptionItemValueAndGroup",
                query = "SELECT ai FROM ArticularItem ai " +
                        "JOIN FETCH ai.optionItems oi " +
                        "JOIN FETCH oi.optionGroup og " +
                        "JOIN FETCH og.optionItems " +
                        "WHERE ai.articularId = :articularId"
        )
})
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticularItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "articular_id", unique = true, nullable = false)
    private String articularId;
    private long dateOfCreate;
    private String productName;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "articular_item_option_item",
            joinColumns = {@JoinColumn(name = "articular_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_item_id")}
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionItem> optionItems = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Price fullPrice;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Price totalPrice;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "status_id")
    private ArticularStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "discount_id")
    private Discount discount;

//    @PrePersist
//    protected void onPrePersist() {
//        if (this.articularId == null) {
//            this.articularId = getUUID();
//        }
//    }

    public void addOptionItem(OptionItem optionItem) {
        this.optionItems.add(optionItem);
        optionItem.getArticularItems().add(this);
    }

    public void removeOptionItem(OptionItem optionItem) {
        this.optionItems.remove(optionItem);
        optionItem.getArticularItems().remove(this);
    }
}
