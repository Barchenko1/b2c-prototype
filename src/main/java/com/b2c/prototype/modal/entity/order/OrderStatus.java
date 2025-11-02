package com.b2c.prototype.modal.entity.order;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
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
                name = "OrderStatus.findByKey",
                query = "SELECT o FROM OrderStatus o WHERE o.key = :key"
        ),
        @NamedQuery(
                name = "OrderStatus.all",
                query = "SELECT o FROM OrderStatus o"
        )
})
public class OrderStatus extends AbstractConstantEntity {
}
