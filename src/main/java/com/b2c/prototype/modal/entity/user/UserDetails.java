package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.AbstractUserDetails;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_profile")
@Data
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "UserProfile.findByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.contactInfo " +
                        "LEFT JOIN FETCH u.addresses " +
                        "LEFT JOIN FETCH u.creditCardList " +
                        "WHERE u.userId = :userId"
        )
})
public class UserDetails extends AbstractUserDetails {
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private List<Address> addresses;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "credit_card_id")
    private List<CreditCard> creditCardList = new ArrayList<>();
}
