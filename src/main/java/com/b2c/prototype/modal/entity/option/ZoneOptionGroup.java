package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.address.Country;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "zone_option_group")
@NamedQueries({
        @NamedQuery(
                name = "ZoneOptionGroup.findByValueWithOptionItems",
                query = "SELECT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH og.optionItemCosts oic " +
                        "WHERE og.value = :value"
        ),
        @NamedQuery(
                name = "ZoneOptionGroup.findWithOptionItems",
                query = "SELECT og FROM OptionGroup og " +
                        "LEFT JOIN FETCH og.optionItems oi " +
                        "LEFT JOIN FETCH og.optionItemCosts oic " +
                        "LEFT JOIN FETCH oi.articularItems oia " +
                        "LEFT JOIN FETCH oic.articularItems oica"
        ),
        @NamedQuery(
                name = "ZoneOptionGroup.withOptionItemsAndArticularItems",
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
public class ZoneOptionGroup extends AbstractConstantEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
    @Column(name = "city", nullable = false)
    private String city;
    @OneToMany(mappedBy = "optionGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ZoneOption> zoneOptions = new HashSet<>();

    public void addZoneOption(ZoneOption zoneOption) {
        this.zoneOptions.add(zoneOption);
        zoneOption.setOptionGroup(this);
    }

    public void removeZoneOption(ZoneOption zoneOption) {
        this.zoneOptions.remove(zoneOption);
        zoneOption.setOptionGroup(null);
    }
}
