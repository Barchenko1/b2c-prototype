package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payment_method")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "PaymentMethod.findByValue",
                query = "SELECT p FROM PaymentMethod p WHERE p.value = :value"
        ),
        @NamedQuery(
                name = "PaymentMethod.all",
                query = "SELECT p FROM PaymentMethod p"
        )
})
public class PaymentMethod extends AbstractConstantEntity {
}
