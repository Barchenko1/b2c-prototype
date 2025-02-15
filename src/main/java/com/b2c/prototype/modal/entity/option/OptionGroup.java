package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "option_group")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "optionGroup.withOptionItems",
                attributeNodes = {
                        @NamedAttributeNode(value = "optionItems")
                }
        ),
        @NamedEntityGraph(
                name = "optionGroup.withOptionItemsAndArticularItems",
                attributeNodes = {
                        @NamedAttributeNode(value = "optionItems", subgraph = "optionItem.articularItems")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "optionItem.articularItems",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "articularItems", subgraph = "articularItem.optionItems")
                                }
                        ),
                        @NamedSubgraph(
                                name = "articularItem.optionItems",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "optionItems")
                                }
                        )
                }
        )
})
@NamedQueries({
        @NamedQuery(
                name = "optionGroup.withOptionItemsAndArticularItems",
                query = "SELECT DISTINCT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH oi.articularItems ai " +
                        "LEFT JOIN FETCH ai.optionItems " +
                        "WHERE og.value = :value"
        )
})
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroup extends AbstractConstantEntity {
    @OneToMany(mappedBy = "optionGroup", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Builder.Default
    private List<OptionItem> optionItems = new ArrayList<>();

    public void addOptionItem(OptionItem optionItem) {
        this.optionItems.add(optionItem);
        optionItem.setOptionGroup(this);
    }

    public void removeOptionItem(OptionItem optionItem) {
        this.optionItems.remove(optionItem);
        optionItem.setOptionGroup(null);
    }
}
