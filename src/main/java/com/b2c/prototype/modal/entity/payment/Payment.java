package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.payment.AbstractPayment;
import com.b2c.prototype.modal.entity.item.Discount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractPayment {
    @ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.MERGE)
    private Discount discount;
}
