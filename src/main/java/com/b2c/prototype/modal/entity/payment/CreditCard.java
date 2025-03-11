package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.payment.AbstractCreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "credit_card")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard extends AbstractCreditCard {
    private boolean isActive;
    private String ownerName;
    private String ownerSecondName;
}
