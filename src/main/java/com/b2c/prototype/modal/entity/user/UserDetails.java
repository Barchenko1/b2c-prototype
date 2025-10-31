package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.entity.address.UserAddress;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "UserDetails.findByUserId",
                query = "SELECT DISTINCT u FROM UserDetails u " +
                        "LEFT JOIN FETCH u.contactInfo uc " +
                        "LEFT JOIN FETCH uc.contactPhone ucp " +
                        "LEFT JOIN FETCH ucp.countryPhoneCode ucpc " +
                        "LEFT JOIN FETCH u.userAddresses ua " +
                        "LEFT JOIN FETCH ua.address uac " +
                        "LEFT JOIN FETCH uac.country uacc " +
                        "LEFT JOIN FETCH u.userCreditCards ucc " +
                        "LEFT JOIN FETCH ucc.creditCard uccc " +
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
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private String userId;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private LocalDateTime dateOfCreate;
    private boolean isActive;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_details_id")
    @Builder.Default
    private Set<Device> devices = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_details_id")
    @Builder.Default
    private Set<UserAddress> userAddresses = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_details_id")
    @Builder.Default
    private Set<UserCreditCard> userCreditCards = new HashSet<>();
}
