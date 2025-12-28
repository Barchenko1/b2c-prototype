package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.tenant.Tenant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "option_group")
@NamedQueries({
        @NamedQuery(
                name = "OptionGroup.findByKey",
                query = "SELECT og FROM OptionGroup og " +
                        "WHERE og.key = :key"
        ),
        @NamedQuery(
                name = "OptionGroup.findByKeyWithOptionItems",
                query = "SELECT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH og.timeDurationOptions tdo " +
                        "LEFT JOIN FETCH og.optionItemCosts oic " +
                        "LEFT JOIN FETCH oic.price oicp " +
                        "LEFT JOIN FETCH oicp.currency oicpc " +
                        "LEFT JOIN FETCH tdo.price tdop " +
                        "LEFT JOIN FETCH tdop.currency tdopc " +
                        "WHERE og.key = :key"
        ),
        @NamedQuery(
                name = "OptionGroup.findByRegionAndKey",
                query = "SELECT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.tenant ogt " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH og.timeDurationOptions tdo " +
                        "LEFT JOIN FETCH og.optionItemCosts oic " +
                        "LEFT JOIN FETCH oic.price oicp " +
                        "LEFT JOIN FETCH oicp.currency oicpc " +
                        "LEFT JOIN FETCH tdo.price tdop " +
                        "LEFT JOIN FETCH tdop.currency tdopc " +
                        "WHERE ogt.code = : code and og.key = :key"
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
        )
})
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroup extends AbstractConstantEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;
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

    public void addOptionItemCost(OptionItemCost optionItemCost) {
        this.optionItemCosts.add(optionItemCost);
        optionItemCost.setOptionGroup(this);
    }

    public void removeOptionItemCost(OptionItemCost optionItemCost) {
        this.optionItemCosts.remove(optionItemCost);
        optionItemCost.setOptionGroup(null);
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
