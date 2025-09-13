package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "option_item")
@Data
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "ArticularItem.findByOptionItemValue",
                query = "SELECT ai FROM ArticularItem ai " +
                        "JOIN FETCH ai.optionItems oi " +
                        "JOIN FETCH ai.totalPrice t " +
                        "JOIN FETCH ai.fullPrice f " +
                        "JOIN FETCH t.currency c1 " +
                        "JOIN FETCH f.currency c2 " +
                        "WHERE oi.value = :value"
        )
})
public class OptionItem extends AbstractConstantEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "option_group_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    protected OptionGroup optionGroup;
    @ManyToMany(mappedBy = "optionItems")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    protected Set<ArticularItem> articularItems = new HashSet<>();

    public void addArticularItem(ArticularItem articularItem) {
        this.articularItems.add(articularItem);
        articularItem.getOptionItems().add(this);
    }

    public void removeArticularItem(ArticularItem articularItem) {
        this.articularItems.remove(articularItem);
        articularItem.getOptionItems().remove(this);
    }
}
