package com.b2c.prototype.modal.entity.delivery;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "delivery_type")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "DeliveryType.findByValue",
                query = "SELECT dt FROM DeliveryType dt WHERE dt.value = :value"
        ),
        @NamedQuery(
                name = "DeliveryType.all",
                query = "SELECT dt FROM DeliveryType dt"
        )
})
public class DeliveryType extends AbstractConstantEntity {
}
