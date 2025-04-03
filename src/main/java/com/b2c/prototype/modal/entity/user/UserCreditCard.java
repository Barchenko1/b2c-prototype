package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.entity.payment.CreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_credit_card")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "UserCreditCard.findByCreditCardNumber",
                query = "SELECT c FROM CreditCard c WHERE c.cardNumber =: cardNumber"
        ),
})
public class UserCreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    private boolean isDefault;
}
