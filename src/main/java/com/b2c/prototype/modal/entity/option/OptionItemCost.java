//package com.b2c.prototype.modal.entity.option;
//
//import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
//import com.b2c.prototype.modal.entity.item.ArticularItem;
//import com.b2c.prototype.modal.entity.price.Price;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.NamedQueries;
//import jakarta.persistence.NamedQuery;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import lombok.experimental.SuperBuilder;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "option_item_cost")
//@Data
//@SuperBuilder
//@NoArgsConstructor
//@NamedQueries({
//        @NamedQuery(
//                name = "ArticularItem.findByOptionItemCostValue",
//                query = "SELECT ai FROM ArticularItem ai " +
//                        "JOIN FETCH ai.optionItems oi " +
//                        "WHERE oi.value = :value"
//        )
//})
//public class OptionItemCost extends AbstractConstantEntity {
//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumn(name = "option_group_id", nullable = false)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private OptionGroup optionGroup;
//    @ManyToMany(mappedBy = "optionItemCosts")
//    @Column(nullable = false)
//    @Builder.Default
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Set<ArticularItem> articularItems = new HashSet<>();
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(nullable = false)
//    private Price fullPrice;
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(nullable = false)
//    private Price totalPrice;
//
//    public void addArticularItem(ArticularItem articularItem) {
//        this.articularItems.add(articularItem);
//        articularItem.getOptionItemCosts().add(this);
//    }
//
//    public void removeArticularItem(ArticularItem articularItem) {
//        this.articularItems.remove(articularItem);
//        articularItem.getOptionItemCosts().remove(this);
//    }
//}
