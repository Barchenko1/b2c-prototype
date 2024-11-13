package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.AbstractCreditCard;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "credit_card")
@Data
@SuperBuilder
@NoArgsConstructor
public class CreditCard extends AbstractCreditCard {

}
