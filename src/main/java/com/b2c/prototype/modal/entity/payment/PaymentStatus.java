package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payment_status")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "PaymentStatus.findByValue",
                query = "SELECT p FROM PaymentStatus p WHERE p.value = :value"
        ),
        @NamedQuery(
                name = "PaymentStatus.all",
                query = "SELECT p FROM PaymentStatus p"
        )
})
public class PaymentStatus extends AbstractConstantEntity {
}
