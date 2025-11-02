package com.b2c.prototype.modal.entity.price;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "currency")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Currency.findByKey",
                query = "SELECT c FROM Currency c WHERE c.key = :key"
        ),
        @NamedQuery(
                name = "Currency.all",
                query = "SELECT c FROM Currency c"
        )
})
public class Currency extends AbstractConstantEntity {
}
