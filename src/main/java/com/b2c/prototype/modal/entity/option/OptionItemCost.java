package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.dao.AbstractEntityDao;
import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.price.Price;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name = "option_item_cost")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OptionItemCost extends AbstractConstantEntity {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Price price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "option_group_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    protected OptionGroup optionGroup;
    @ManyToMany(mappedBy = "optionItemCosts")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    protected Set<ArticularItem> articularItems = new HashSet<>();

    public void addArticularItem(ArticularItem articularItem) {
        this.articularItems.add(articularItem);
        articularItem.getOptionItemCosts().add(this);
    }

    public void removeArticularItem(ArticularItem articularItem) {
        this.articularItems.remove(articularItem);
        articularItem.getOptionItemCosts().remove(this);
    }
}
