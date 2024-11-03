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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String name;
    private String articularId;
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ItemType itemType;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ItemStatus status;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_data_option",
            joinColumns = {@JoinColumn(name = "itemdata_id")},
            inverseJoinColumns = {@JoinColumn(name = "optionitem_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<OptionItem> optionItemSet = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Price price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private CurrencyDiscount currencyDiscount;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private PercentDiscount percentDiscount;

    public void addOptionItem(OptionItem optionItem) {
        this.optionItemSet.add(optionItem);
        optionItem.getItemDataSet().add(this);
    }

    public void removeOptionItem(OptionItem optionItem) {
        this.optionItemSet.remove(optionItem);
        optionItem.getItemDataSet().remove(this);
    }
}
