package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "item_type")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "ItemType.findByKey",
                query = "SELECT i FROM ItemType i WHERE i.key = :key"
        ),
        @NamedQuery(
                name = "ItemType.all",
                query = "SELECT i FROM ItemType i"
        )
})
public class ItemType extends AbstractConstantEntity {

}
