package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
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
                query = "SELECT ai FROM ArticularItem ai JOIN FETCH ai.optionItems oi WHERE oi.value = :value"
        )
})
public class OptionItem extends AbstractConstantEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "option_group_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private OptionGroup optionGroup;
    @ManyToMany(mappedBy = "optionItems", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Builder.Default
    @ToString.Exclude
    private Set<ArticularItem> articularItems = new HashSet<>();
}
