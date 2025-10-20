package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "option_group")
@NamedQueries({
        @NamedQuery(
                name = "OptionGroup.findByValue",
                query = "SELECT og FROM OptionGroup og " +
                        "WHERE og.value = :value"
        ),
        @NamedQuery(
                name = "OptionGroup.findByValueWithOptionItems",
                query = "SELECT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH og.timeDurationOptions tdo " +
                        "LEFT JOIN FETCH og.optionItemCosts oic " +
                        "LEFT JOIN FETCH oic.price oicp " +
                        "LEFT JOIN FETCH oicp.currency oicpc " +
                        "LEFT JOIN FETCH tdo.price tdop " +
                        "LEFT JOIN FETCH tdop.currency tdopc " +
                        "WHERE og.value = :value"
        ),
        @NamedQuery(
                name = "OptionGroup.withOptionItems",
                query = "SELECT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH og.timeDurationOptions tdo " +
                        "LEFT JOIN FETCH og.optionItemCosts oic " +
                        "LEFT JOIN FETCH oic.price oicp " +
                        "LEFT JOIN FETCH oicp.currency oicpc " +
                        "LEFT JOIN FETCH tdo.price tdop " +
                        "LEFT JOIN FETCH tdop.currency tdopc"
        ),
        @NamedQuery(
                name = "OptionGroup.withOptionItemsAndArticularItems",
                query = "SELECT DISTINCT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH oi.articularItems ai " +
                        "LEFT JOIN FETCH ai.optionItems ao " +
                        "LEFT JOIN FETCH ai.totalPrice t " +
                        "LEFT JOIN FETCH ai.fullPrice f " +
                        "LEFT JOIN FETCH t.currency c1 " +
                        "LEFT JOIN FETCH f.currency c2 " +
                        "WHERE og.value = :value"
        )
})
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroup extends AbstractConstantEntity {
    @OneToMany(mappedBy = "optionGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OptionItem> optionItems = new HashSet<>();
    @OneToMany(mappedBy = "optionGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OptionItemCost> optionItemCosts = new HashSet<>();
    @OneToMany(mappedBy = "optionGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<TimeDurationOption> timeDurationOptions = new HashSet<>();

    public void addOptionItem(OptionItem optionItem) {
        this.optionItems.add(optionItem);
        optionItem.setOptionGroup(this);
    }

    public void removeOptionItem(OptionItem optionItem) {
        this.optionItems.remove(optionItem);
        optionItem.setOptionGroup(null);
    }

    public void addOptionItemCost(OptionItemCost optionItem) {
        this.optionItemCosts.add(optionItem);
        optionItem.setOptionGroup(this);
    }

    public void removeOptionItemCost(OptionItemCost optionItem) {
        this.optionItemCosts.remove(optionItem);
        optionItem.setOptionGroup(null);
    }

    public void addTimeDurationOption(TimeDurationOption timeDurationOption) {
        this.timeDurationOptions.add(timeDurationOption);
        timeDurationOption.setOptionGroup(this);
    }

    public void removeTimeDurationOption(TimeDurationOption timeDurationOption) {
        this.timeDurationOptions.remove(timeDurationOption);
        timeDurationOption.setOptionGroup(null);
    }

}
