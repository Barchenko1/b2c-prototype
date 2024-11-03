package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.entity.item.ItemData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "option_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String optionName;
    @ManyToMany(mappedBy = "optionItemSet", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Builder.Default
    @ToString.Exclude
    private Set<ItemData> itemDataSet = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private OptionGroup optionGroup;

    public void addItem(ItemData itemData) {
        this.itemDataSet.add(itemData);
        itemData.getOptionItemSet().add(this);
    }

    public void removeItem(ItemData itemData) {
        this.itemDataSet.remove(itemData);
        itemData.getOptionItemSet().remove(this);
    }

}
