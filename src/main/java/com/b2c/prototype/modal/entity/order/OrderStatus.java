package com.b2c.prototype.modal.entity.order;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_status")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "OrderStatus.findByValue",
                query = "SELECT o FROM OrderStatus o WHERE o.value = : value"
        )
})
public class OrderStatus extends AbstractConstantEntity {
}
