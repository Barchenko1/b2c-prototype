package com.b2c.prototype.modal.client.entity.option;

import com.b2c.prototype.modal.client.entity.item.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "option_item")
@Data
public class OptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Item> items;
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionGroup optionGroup;
}
