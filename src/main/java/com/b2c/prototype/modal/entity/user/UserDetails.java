package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.user.AbstractUserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_details")
@Data
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "UserDetails.findByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.contactInfo " +
                        "LEFT JOIN FETCH u.userAddresses " +
                        "LEFT JOIN FETCH u.userCreditCards " +
                        "LEFT JOIN FETCH u.devices d " +
                        "WHERE u.userId = :userId"
        ),
        @NamedQuery(
                name = "UserDetails.findAddressesByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.contactInfo ci " +
                        "LEFT JOIN FETCH u.userAddresses ua " +
                        "LEFT JOIN FETCH ua.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "WHERE u.userId = :userId"
        ),
        @NamedQuery(
                name = "UserDetails.findUserCreditCardsByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.contactInfo ci " +
                        "LEFT JOIN FETCH u.userCreditCards ucard " +
                        "LEFT JOIN FETCH ucard.creditCard c " +
                        "WHERE u.userId = :userId"
        ),
        @NamedQuery(
                name = "UserDetails.findFullUserDetailsByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.contactInfo ci " +
                        "LEFT JOIN FETCH ci.contactPhone cp " +
                        "LEFT JOIN FETCH cp.countryPhoneCode cpc " +
                        "LEFT JOIN FETCH u.userAddresses " +
                        "LEFT JOIN FETCH u.userCreditCards " +
                        "LEFT JOIN FETCH u.devices d " +
                        "WHERE u.userId = :userId"
        ),
        @NamedQuery(
                name = "UserDetails.findAllFullUserDetailsByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.contactInfo ci " +
                        "LEFT JOIN FETCH ci.contactPhone cp " +
                        "LEFT JOIN FETCH cp.countryPhoneCode cpc " +
                        "LEFT JOIN FETCH u.userAddresses " +
                        "LEFT JOIN FETCH u.userCreditCards " +
                        "LEFT JOIN FETCH u.devices d "
        ),
        @NamedQuery(
                name = "UserDetails.findAllDevicesByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.devices d " +
                        "WHERE u.userId = :userId"
        )
})
public class UserDetails extends AbstractUserDetails {

}
