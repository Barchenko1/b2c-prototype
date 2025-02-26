package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.AbstractCreditCard;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "credit_card")
@Data
@SuperBuilder
@NoArgsConstructor
public class UserCreditCard extends AbstractCreditCard {
    private boolean isDefault;
}
