package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.entity.item.Discount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "payment_uniq_id", unique = true, nullable = false)
    private String paymentUniqId;
    private LocalDateTime paymentTime;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MultiCurrencyPriceInfo commissionPriceInfo;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private MultiCurrencyPriceInfo fullMultiCurrencyPriceInfo;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private MultiCurrencyPriceInfo totalMultiCurrencyPriceInfo;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MultiCurrencyPriceInfo discountMultiCurrencyPriceInfo;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name = "payment_status_id")
    private PaymentStatus paymentStatus;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})

    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    @ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.MERGE)
    private Discount discount;
}
