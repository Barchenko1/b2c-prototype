package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "articular_status")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "ArticularStatus.findByValue",
                query = "SELECT a FROM ArticularStatus a WHERE a.value = : value"
        )
})
public class ArticularStatus extends AbstractConstantEntity {

}
