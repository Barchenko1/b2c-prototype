package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "count_type")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "CountType.findByValue",
                query = "SELECT c FROM CountType c WHERE c.value = : value"
        )
})
public class CountType extends AbstractConstantEntity {
}
