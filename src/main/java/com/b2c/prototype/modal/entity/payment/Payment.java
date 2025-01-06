package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.base.AbstractPayment;
import com.b2c.prototype.modal.entity.item.Discount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractPayment {
    @Column(name = "upayment_id", unique = true, nullable = false)
    private String paymentId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    @ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.MERGE)
    private Discount discount;

    @PrePersist
    protected void onPrePersist() {
        if (this.paymentId == null) {
            this.paymentId = getUUID();
        }
    }
}
