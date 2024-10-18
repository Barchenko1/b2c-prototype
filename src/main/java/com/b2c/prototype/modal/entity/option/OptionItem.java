package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.entity.item.Item;
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
    @ManyToMany(mappedBy = "optionItems", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Builder.Default
    @ToString.Exclude
    private Set<Item> items = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private OptionGroup optionGroup;

    public void addItem(Item item) {
        this.items.add(item);
        item.getOptionItems().add(this);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        item.getOptionItems().remove(this);
    }

//    @PreRemove
//    private void preRemove() {
//        if (items != null && !items.isEmpty()) {
//            for (Item item : items) {
//                item.getOptionItems().remove(this);
//            }
//        }
//    }
}
