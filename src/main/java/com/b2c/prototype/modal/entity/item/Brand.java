package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "brand")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Brand.findByValue",
                query = "SELECT b FROM Brand b WHERE b.value = : value"
        )
})
public class Brand extends AbstractConstantEntity {

}
